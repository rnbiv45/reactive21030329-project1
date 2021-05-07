package com.rbierly.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rbierly.auth.Auth;
import com.rbierly.beans.Application;
import com.rbierly.beans.Application.ApplicationStep;
import com.rbierly.beans.Employee;
import com.rbierly.beans.Employee.EmployeeRole;
import com.rbierly.controllers.EmployeeController;
import com.rbierly.data.ApplicationDao;
import com.rbierly.data.InMemoryApplicationDao;
import com.rbierly.data.KeyspaceApplicationDao;
import com.rbierly.utils.EmailUtil;
import com.rbierly.utils.S3Util;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import jdk.internal.net.http.common.Log;

public class ApplicationServiceImp implements ApplicationService {
	private static final Logger log = LogManager.getLogger(ApplicationService.class);
	//private static EmployeeService employeeService = new EmployeeServiceImp(); 
	private static ApplicationDao applicationDao = new KeyspaceApplicationDao();
	private static EmployeeService employeeService = new EmployeeServiceImp();
	private static S3Util s3util = S3Util.getInstance();
	private static ExecutorService executorService = 
			new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	@Override
	public void deleteById(Employee loggedInEmployee, Integer id) {
		log.trace("deleting employee "+id.toString());
		Application app = getById(loggedInEmployee, id);
		Employee emp = employeeService.getById(app.getEmployeeId());
		emp.setAvailableReimbursement(Math.min(1000.0f, emp.getAvailableReimbursement()+app.getAwardAmount()));
		applicationDao.delete(id, app.getEmployeeId());
	}

	@Override
	public List<Application> getMany(Integer startIndex, Integer endIndex) {
		return applicationDao.getMany(startIndex, endIndex);
	}

	@Override
	public Application getById(Employee loggedInEmployee, Integer validInt) {
		log.trace(loggedInEmployee.getId().toString()+" getting application "+validInt.toString());
		Application app = applicationDao.getById(validInt);
		if (app == null) {
			throw new NotFoundResponse();
		}
		if (!Auth.isInterestedParty(loggedInEmployee.getId(), app)) {
			throw new UnauthorizedResponse();
		}
		return app;
	}

	@Override
	public void update(Application application, Integer validInt) {
		log.trace("updating application "+validInt.toString());
		applicationDao.update(application, validInt);
	}

	@Override
	public List<Application> getAllByEmployeeId(Integer id) {
		return applicationDao.getAllByEmployeeId(id);
	}

	@Override
	public List<Application> getAllBySupervisorId(Integer id) {
		return applicationDao.getAllBySupervisorId(id);
	}
	
//	@Override
//	public void decision(Integer empId, Integer appId, Boolean decision, String reason) {
//		Application application = getById(appId);
//		Employee supervisor = employeeService.getById(empId);
//		switch (supervisor.getRole()) {
//		case SUPERVISOR:
//			if (supervisor.getId().equals(application.getSupervisorId())) {
//				application.setDeniedBySupervisor(decision);
//			}
//			if (decision == false) {
//				application.setSupervisorDenialReason(reason);
//			}
//			break;
//		case DEPARTMENT_HEAD:
//			if (supervisor.getId().equals(application.getDepartmentHeadId())) {
//				application.setDeniedByDepartmentHead(decision);
//			}
//			if (decision == false) {
//				application.setDepartmentHeadDenialReason(reason);
//			}
//			break;
//		case BEN_CO:
//			if (supervisor.getId().equals(application.getBencoId())) {
//				application.setDeniedByBenco(decision);
//			}
//			if (decision == false) {
//				application.setBencoDenialReason(reason);
//			}
//			break;
//		default:
//			throw new BadRequestResponse();
//		}
//	}

	@Override
	public void submitApplication(Employee loggedInEmployee, Application application, List<UploadedFile> attachments) {
		log.trace(loggedInEmployee.getId().toString()+" submitting application");
		if (attachments.size() > 0) {
			List<String> attachmentNames = new ArrayList<String>();
			for (UploadedFile attachment : attachments) {
				String name = attachment.getFilename()+attachment.getExtension();
				attachmentNames.add(name);
				try {
					s3util.uploadToBucket(name, attachment.getContent().readAllBytes());
				} catch (IOException e) {
					throw new InternalServerErrorResponse("File "+name+" could not be saved");
				}
			}
			application.setDeniedBySupervisor(false);
			application.setStep(ApplicationStep.DEPT_HEAD);
			application.setSupervisorDenialReason("");
			application.setAttachments(attachmentNames);
		}
		if (loggedInEmployee.getAvailableReimbursement() < application.getAwardAmount()) {
			application.setAwardAmount(loggedInEmployee.getAvailableReimbursement());
		}
		loggedInEmployee.setAvailableReimbursement(
				loggedInEmployee.getAvailableReimbursement()
				- application.getAwardAmount());
		loggedInEmployee.setAvailableReimbursement(
				Math.round(loggedInEmployee.getAvailableReimbursement() * 100.0f) / 100.0f);
		employeeService.update(loggedInEmployee, loggedInEmployee.getId());
		applicationDao.create(application);
	}
	

	@Override
	public void approval(Integer id, Employee loggedInEmployee, Boolean denied, String reason) {
		Application app = getById(loggedInEmployee, id);
		Employee appEmpl = employeeService.getById(app.getEmployeeId());
		
		if (app.getStep().equals(ApplicationStep.SUPERVISOR) && app.getSupervisorId().equals(loggedInEmployee.getId())) {
			if (denied) {
				if (reason == null) {
					throw new BadRequestResponse("Denial reason cannot be null");
				}
				app.setSupervisorDenialReason(reason);
				executorService.execute(() -> {
					EmailUtil.sendEmail(
							appEmpl.getEmail(), 
							"Application denied by your Supervisor", 
							"Denied for the following reason: "+reason);	
				});
				log.trace(id.toString()+" has been denied by "+loggedInEmployee.getId().toString());
			} else {
				app.setSupervisorDenialReason("");
				if (app.getDepartmentHeadId().equals(loggedInEmployee.getId())) {
					app.setDeniedByDepartmentHead(denied);
					app.setStep(ApplicationStep.BEN_CO);
					app.setDepartmentHeadDenialReason("");
				} else {
					app.setStep(ApplicationStep.DEPT_HEAD);
				}
				log.trace(id.toString()+" has been approved by "+loggedInEmployee.getId().toString());
			}
			app.setDeniedBySupervisor(denied);
			update(app, app.getId());
			return;
		}
		
		if (app.getStep().equals(ApplicationStep.DEPT_HEAD) && app.getDepartmentHeadId().equals(loggedInEmployee.getId())) {
			if (denied) {
				if (reason == null) {
					throw new BadRequestResponse("Denial reason cannot be null");
				}
				app.setDepartmentHeadDenialReason(reason);
				executorService.execute(() -> {
					EmailUtil.sendEmail(
							appEmpl.getEmail(), 
							"Application denied by your Department Head", 
							"Denied for the following reason: "+reason);
				});
				log.trace(id.toString()+" has been denied by "+loggedInEmployee.getId().toString());
			} else {
				app.setDepartmentHeadDenialReason("");
				app.setStep(ApplicationStep.BEN_CO);
				log.trace(id.toString()+" has been approved by "+loggedInEmployee.getId().toString());
			}
			app.setDeniedByDepartmentHead(denied);
			update(app, app.getId());
			return;
		}
		
		if (app.getStep().equals(ApplicationStep.BEN_CO) && app.getBencoId().equals(loggedInEmployee.getId())) {
			if (denied) {
				if (reason == null) {
					throw new BadRequestResponse("Denial reason cannot be null");
				}
				app.setBencoDenialReason(reason);
				executorService.execute(() -> {
					EmailUtil.sendEmail(
							appEmpl.getEmail(), 
							"Application denied by the Benefits Coordinator", 
							"Denied for the following reason: "+reason);
				});
				log.trace(id.toString()+" has been denied by "+loggedInEmployee.getId().toString());
			} else {
				app.setBencoDenialReason("");
				app.setStep(ApplicationStep.PENDING);
				executorService.execute(() -> {
					EmailUtil.sendEmail(
							appEmpl.getEmail(), 
							"Application approved!", 
							"You will receive $"+app.getAwardAmount().toString()+" in reimbursement upon completion of the event");
				});
				//TODO send the money
				log.trace(id.toString()+" has been approved by "+loggedInEmployee.getId().toString());
			}
			app.setDeniedByBenco(denied);
			update(app, app.getId());
			return;
		}
		
		if (app.getStep().equals(ApplicationStep.PENDING) && app.getBencoId().equals(loggedInEmployee.getId())) {
			if (!denied) {
				app.setApproved(true);
			}
		}
		
		throw new UnauthorizedResponse("Unauthorized: application is in "+app.getStep().toString()+" step");
	}

	@Override
	public void updateAward(Employee loggedInEmployee, Application app, Float award, String reason) {
		Employee emp = employeeService.getById(app.getEmployeeId());
		Float awardDiff = app.getAwardAmount() - award;
		if (!EmployeeRole.BEN_CO.equals(loggedInEmployee.getRole())) {
			throw new UnauthorizedResponse("Only Bencos can update award amount");
		}
		app.setAwardAmount(award);
		emp.setAvailableReimbursement(emp.getAvailableReimbursement()+awardDiff);
		if (emp.getAvailableReimbursement() < 0) {
			app.setExceedsAvailableFunds(true);
			app.setExceedingFundsReason(reason);
			emp.setAvailableReimbursement(0.0f);
		} else {
			app.setExceedsAvailableFunds(false);
			app.setExceedingFundsReason("");
		}
		executorService.execute(() -> {
			EmailUtil.sendEmail(
					emp.getEmail(), 
					"Award amount changed", 
					"Your Benefits Coordinator has changed your award amount to $"
					+app.getAwardAmount().toString());
		});
		update(app, app.getId());
		employeeService.update(emp, emp.getId());
		log.trace(loggedInEmployee.getId().toString()+" updated award amount to $"+award.toString());
	}
}
