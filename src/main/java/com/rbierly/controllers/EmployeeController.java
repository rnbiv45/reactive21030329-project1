package com.rbierly.controllers;

import static com.rbierly.utils.InputValidation.contextBodyNullorEmpty;
import static com.rbierly.utils.InputValidation.jsonIsValid;
import static com.rbierly.utils.InputValidation.idNotNull;
import static com.rbierly.utils.InputValidation.queryParamForInt;
import static com.rbierly.utils.InputValidation.validInt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rbierly.beans.Employee;
import com.rbierly.services.EmployeeService;
import com.rbierly.services.EmployeeServiceImp;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.core.validation.BodyValidator;
import io.javalin.http.Context;

public class EmployeeController implements CrudHandler {
	private static final Logger log = LogManager.getLogger(EmployeeController.class);
	private static EmployeeService employeeService = new EmployeeServiceImp();
	
	private static final Integer defaultOffset = 0;
	private static final Integer defaultAmount = 10;
	
	@Override
	public void create(Context ctx) {
		log.trace("create");
		contextBodyNullorEmpty(ctx);
		BodyValidator<Employee> bodyValidator = ctx.bodyValidator(Employee.class);
		jsonIsValid(bodyValidator);
		employeeService.create(bodyValidator.get());
		ctx.result("Employee created");
		ctx.status(201);
	}

	@Override
	public void delete(Context ctx, String id) {
		log.trace("delete");
		idNotNull(id);
		employeeService.delete(validInt(id));
		ctx.status(204);
		ctx.result("Employee deleted");
	}

	@Override
	public void getAll(Context ctx) {
		Integer offset = queryParamForInt(ctx, "offset", defaultOffset);
		Integer amount = queryParamForInt(ctx, "amount", defaultAmount);
		Integer endIndex = offset+amount;
		log.trace("get employees from "+offset.toString()+" to "+endIndex.toString());
		ctx.json(employeeService.getMany(offset, endIndex));
		ctx.status(200);
	}

	@Override
	public void getOne(Context ctx, String id) {
		log.trace("getOne");
		idNotNull(id);
		ctx.json(employeeService.getById(validInt(id)));
		ctx.status(200);
	}
	
	@Override
	public void update(Context ctx, String id) {
		log.trace("update");
		idNotNull(id);
		BodyValidator<Employee> bodyValidator = ctx.bodyValidator(Employee.class);
		jsonIsValid(bodyValidator);
		employeeService.update(bodyValidator.get(), validInt(id));
		ctx.status(201);
		ctx.result("Employee updated");
	}

}
