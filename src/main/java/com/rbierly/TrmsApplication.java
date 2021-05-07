package com.rbierly;

import static io.javalin.apibuilder.ApiBuilder.crud;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

import java.util.Set;

import com.rbierly.auth.Auth;
import com.rbierly.beans.Application.EventType;
import com.rbierly.beans.Application.GradingFormat;
import com.rbierly.beans.Employee;
import com.rbierly.beans.Employee.EmployeeRole;
import com.rbierly.controllers.ApplicationController;
import com.rbierly.controllers.AttachmentController;
import com.rbierly.controllers.EmployeeController;
import com.rbierly.utils.Cassandra;
import com.rbierly.utils.EmailUtil;

import io.javalin.Javalin;
import io.javalin.core.security.Role;
import io.javalin.core.validation.JavalinValidation;


public class TrmsApplication {
	private static Set<Role> employeeRoles = Auth.roles(EmployeeRole.REGULAR,EmployeeRole.SUPERVISOR,EmployeeRole.DEPARTMENT_HEAD,EmployeeRole.BEN_CO);
	//private static Set<Role> approvalRoles = Auth.roles(EmployeeRole.SUPERVISOR,EmployeeRole.DEPARTMENT_HEAD, EmployeeRole.BEN_CO);
	
	public static void main(String[] args) {
		javalin();
	}
	
	public static void javalin() {
		Javalin app = Javalin.create().start(8080);
		app.config.accessManager(Auth::accessManager);

		JavalinValidation.register(GradingFormat.class, v -> GradingFormat.valueOf(v));
		JavalinValidation.register(EventType.class, v -> EventType.valueOf(v));
		
		app.routes(() -> {
			crud("employees/:employee-id", new EmployeeController(), employeeRoles);
			crud("apps/:application-id", new ApplicationController(), employeeRoles);
			crud("attachments/:attachment-name", new AttachmentController(), employeeRoles);
		});
		
		Cassandra.getInstance();
	}
	
}
