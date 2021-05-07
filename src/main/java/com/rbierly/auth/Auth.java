package com.rbierly.auth;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rbierly.beans.Application;
import com.rbierly.beans.Employee;
import com.rbierly.beans.Employee.EmployeeRole;
import com.rbierly.services.EmployeeService;
import com.rbierly.services.EmployeeServiceImp;

import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;

public class Auth {
	private static final Logger log = LogManager.getLogger(Auth.class);
	private static EmployeeService employeeService = new EmployeeServiceImp();
	
	public static Boolean isInterestedParty(Integer id, Application application) {
		Boolean result = false;
		if (application.getEmployeeId().equals(id)) {
			result = true;
		}
		if (application.getSupervisorId().equals(id)) {
			result = true;
		}
		if (application.getDepartmentHeadId().equals(id)) {
			result = true;
		}
		if (application.getBencoId().equals(id)) {
			result = true;
		}
		return result;
	}
	
	public static Employee loggedInEmployee(Context ctx) {
		Integer id = ctx.header("Access-Token", Integer.class).get();
		if (id == null) {
			throw new UnauthorizedResponse();
		}
		Employee employee = employeeService.getById(id);
		if (employee == null) {
			throw new UnauthorizedResponse();
		}
		return employee;
	}
	
	public static Set<Role> roles(EmployeeRole... roleArgs) {
		Set<Role> roles = new HashSet<Role>();
		for (EmployeeRole role : roleArgs) {
			roles.add(role);
		}
		return roles;
	}
	
	public static EmployeeRole getEmployeeRole(Context ctx) {
		log.trace("Checking for access token");
		Employee employee = null;
		employee = loggedInEmployee(ctx);
		if (employee == null) {
			return EmployeeRole.TERMINATED;
		}
		return employee.getRole();
	}
	
	public static void accessManager(Handler handler, Context ctx, Set<Role> permittedRoles) throws Exception {
		EmployeeRole role = getEmployeeRole(ctx);
		log.trace("User Role: "+role);
		if (permittedRoles.contains(role)) {
			handler.handle(ctx);
		} else {
			throw new UnauthorizedResponse();
		}
	}
}
