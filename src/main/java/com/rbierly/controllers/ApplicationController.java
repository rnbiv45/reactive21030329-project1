package com.rbierly.controllers;

import static com.rbierly.utils.InputValidation.idNotNull;
import static com.rbierly.utils.InputValidation.jsonIsValid;
import static com.rbierly.utils.InputValidation.queryParamForInt;
import static com.rbierly.utils.InputValidation.validInt;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rbierly.auth.Auth;
import com.rbierly.beans.Application;
import com.rbierly.beans.Application.ApplicationStep;
import com.rbierly.beans.Application.EventType;
import com.rbierly.beans.Application.GradingFormat;
import com.rbierly.beans.Employee;
import com.rbierly.beans.Employee.EmployeeRole;
import com.rbierly.services.ApplicationService;
import com.rbierly.services.ApplicationServiceImp;
import com.rbierly.services.EmployeeService;
import com.rbierly.services.EmployeeServiceImp;
import com.rbierly.utils.S3Util;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.core.validation.BodyValidator;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

public class ApplicationController implements CrudHandler {
	private static final Logger log = LogManager.getLogger(ApplicationController.class);
	private static ApplicationService applicationService = new ApplicationServiceImp();
	private static EmployeeService employeeService = new EmployeeServiceImp();
	
	private static final Integer defaultOffset = 0;
	private static final Integer defaultAmount = 10;
	
	@Override
	public void create(Context ctx) {
		log.trace("create");
		Application newApplication = new Application();
		newApplication.setId(ctx.formParam("Application-Id", Integer.class).get());
		newApplication.setEmployeeId(ctx.formParam("Employee-Id", Integer.class).get());
		newApplication.setSupervisorId(ctx.formParam("Supervisor-Id", Integer.class).get());
		newApplication.setDepartmentHeadId(ctx.formParam("Department-Head-Id", Integer.class).get());
		newApplication.setBencoId(ctx.formParam("Benco-Id", Integer.class).get());
		newApplication.setLocation(ctx.formParam("Location", String.class).get());
		newApplication.setDescription(ctx.formParam("Description", String.class).get());
		newApplication.setCost(ctx.formParam("Cost", Float.class).get());
		newApplication.setGradingFormat(ctx.formParam("Grading-Format", GradingFormat.class).get());
		newApplication.setEventType(ctx.formParam("Event-Type", EventType.class).get());
		newApplication.setJustification(ctx.formParam("Justification", String.class).get());
		newApplication.setStep(ApplicationStep.SUPERVISOR);
		newApplication.setDateTime(LocalDateTime.now().toString());
		newApplication.setAwardAmount(newApplication.getCost() * (float) newApplication.getEventType().getPercent());
		newApplication.setExceedsAvailableFunds(false);
		newApplication.setExceedingFundsReason("");
		List<UploadedFile> attachments = new ArrayList<UploadedFile>();
		try {
			attachments = ctx.uploadedFiles("Attachments");
		} catch (IllegalStateException e) {}
		
		
		
		applicationService.submitApplication(Auth.loggedInEmployee(ctx), newApplication, attachments);
		ctx.status(201);
		ctx.result("Application submitted");
	}
	
//	public static void submitted(Context ctx) {
//		applicationService.getAllByEmployeeId(
//				ctx.header("Access-Token", Integer.class).get());
//	}
//	
//	public static void requested(Context ctx) {
//		applicationService.getAllBySupervisorId(
//				ctx.header("Access-Token", Integer.class).get());
//	}
//	
//	public static void decision(Context ctx) {
//		applicationService.decision(
//				ctx.header("Access-Token", Integer.class).get(),
//				ctx.pathParam("app-id", Integer.class).get(),
//				ctx.formParam("approved", Boolean.class).get(),
//				ctx.formParam("reason", String.class, "Unfortunate").get());
//	}

	@Override
	public void delete(Context ctx, String id) {
		log.trace("delete");
		idNotNull(id);
		applicationService.deleteById(Auth.loggedInEmployee(ctx), validInt(id));
		ctx.status(204);
		ctx.result("Application deleted");
	}

	@Override
	public void getAll(Context ctx) {
		log.trace("getAll");
		//Integer userId = ctx.header(", null)
		Integer offset = queryParamForInt(ctx, "offset", defaultOffset);
		Integer amount = queryParamForInt(ctx, "amount", defaultAmount);
		ctx.json(applicationService.getMany(offset, offset+amount));
		ctx.status(200);
	}

	@Override
	public void getOne(Context ctx, String id) {
		log.trace("getOne");
		idNotNull(id);
		ctx.json(applicationService.getById(Auth.loggedInEmployee(ctx), validInt(id)));
		ctx.status(200);
	}

	@Override
	public void update(Context ctx, String id) {
		log.trace("update");
		Boolean denied = ctx.formParam("Denied", Boolean.class).getOrNull();
		Float award = ctx.formParam("Award", Float.class).getOrNull();
		
		if (denied != null) {
			Employee loggedInEmployee =  Auth.loggedInEmployee(ctx);
			String reason = ctx.formParam("Denial-Reason", String.class).getOrNull();
			applicationService.approval(validInt(id), loggedInEmployee, denied, reason);
			ctx.status(200);
			ctx.result("Application updated");
			return;
		}
		if (award != null) {
			String reason = ctx.formParam("Reason", String.class, "Default Reason").get();
			Application app = applicationService.getById(Auth.loggedInEmployee(ctx), validInt(id));
			applicationService.updateAward(Auth.loggedInEmployee(ctx), app, award, reason);
			ctx.status(200);
			ctx.result("Application updated");
			return;
		}
		ctx.status(200);
		ctx.result("Nothing updated");
	}
	
	public static void getApplicationsByUser(Context ctx) {
		log.trace("getApplicationsByUser");
		// TODO
	}
	
	public static void getCurrentUsersPendingApplications(Context ctx) {
		log.trace("getUsersPendingApplications");
		// TODO
	}
	
	public static void getCurrentUsersAppsNeedingApproval(Context ctx) {
		log.trace("getCurrentUsersAppsNeedingApproval");
	}
	
	public static void getSubmittedApps(Context ctx) {
		Employee employee = Auth.loggedInEmployee(ctx);
		
	}
	
	public static void updateSubmittedApp(Context ctx) {
		EmployeeRole role = Auth.getEmployeeRole(ctx);
		switch (role) {
		case SUPERVISOR:
			break;
		case DEPARTMENT_HEAD:
			break;
		case BEN_CO:
			break;
		default:
			
			break;
		}
	}
	

}
