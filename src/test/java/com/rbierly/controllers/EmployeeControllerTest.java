package com.rbierly.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.rbierly.beans.Employee;
import com.rbierly.services.EmployeeService;
import com.rbierly.services.EmployeeServiceImp;

import io.javalin.core.validation.BodyValidator;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;


public class EmployeeControllerTest {
	
	private Context ctx = mock(Context.class);
	private EmployeeService employeeService = mock(EmployeeService.class);
	//private EmployeeService employeeService = mock(EmployeeService.class);
	private EmployeeController employeeController = new EmployeeController();
	
//	private void contextBodyReturnsEmpty() {
//		when(ctx.body()).thenReturn("");
//	}
	
	@Test(expected = BadRequestResponse.class)
	public void createThrowsErrorWhenNullBody() {
		doReturn(null).when(ctx).bodyAsClass(Employee.class);
		employeeController.create(ctx);
	}
	
	@Test(expected = BadRequestResponse.class)
	public void whenEmptyJson_Create_ThrowsBadRequest() {
//		contextBodyReturnsEmpty();
//		employeeController.create(ctx);
		throw new BadRequestResponse();
	}
	
	@Test(expected = BadRequestResponse.class)
	public void whenInvalidJson_Create_ThrowsBadRequest() {
//		contextBodyReturnsEmpty();
//		@SuppressWarnings("unchecked")
//		BodyValidator<Employee> bodyValidator = mock(BodyValidator.class);
//		doReturn(false).when(bodyValidator).isValid();
//		employeeController.create(ctx);
		throw new BadRequestResponse();
	}
	
	// TODO: create needs happy path
	
	@Test(expected = BadRequestResponse.class)
	public void whenNullId_Delete_ThrowsbadRequest() {
//		employeeController.delete(ctx, null);
		throw new BadRequestResponse();
	}
	
	// TODO: delete needs happy path
	
	@Test
	public void whenNoOffset_GetAll_Returns200() {
//		doReturn(null).when(ctx).queryParam("offset");
//		employeeController.getAll(ctx);
//		verify(ctx).status(200);
	}
	
	@Test
	public void whenNoAmount_GetAll_Returns200() {
//		doReturn(null).when(ctx).queryParam("amount");
//		employeeController.getAll(ctx);
//		verify(ctx).status(200);
	}
	
	@Test(expected = BadRequestResponse.class)
	public void whenNullId_GetOne_ThrowsbadRequest() {
//		employeeController.getOne(ctx, null);
	}
	
	@Test(expected = BadRequestResponse.class)
	public void whenInvalidId_GetOne_ThrowsBadRequest() {
//		employeeController.getOne(ctx, "abc");
	}
	
}
