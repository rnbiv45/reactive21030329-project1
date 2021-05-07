package com.rbierly.javalin;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.Test;

import com.rbierly.beans.Employee;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

public class JavalinTest {
	
	private Context ctx = mock(Context.class);
	
	private void whenQueryRequestsThisReturnThat(String request, String response) {
		when(ctx.queryParam(request)).thenReturn(response);
	}
	
	private void verifyContextResult(String result) {
		verify(ctx).result(result);
	}
	
//	private void verifyContextStatus(int status) {
//		verify(ctx).status(status);
//	}
	
//	@Test
//	public void whenNameProvided_GetToHello_GivesName() {
//		whenQueryRequestsThisReturnThat("name", "Bobby");
//		
//		BaseController.hello(ctx);
//		
//		verifyContextResult("Hello, Bobby!");
//	}
//	
//	@Test
//	public void whenNoNameProvided_GetToHello_GivesWorld() {
//		whenQueryRequestsThisReturnThat("name", null);
//		
//		BaseController.hello(ctx);
//		
//		verifyContextResult("Hello, World!");
//	}
	
//	@Test
//	public void whenValidUsername_POSTtoUsers_gives201() {
//		whenQueryRequestsThisReturnThat("username", "Roland");
//		
//		BaseController.create(ctx);
//		
//		verifyContextStatus(201);
//	}
//	
//	@Test(expected = BadRequestResponse.class)
//	public void POST_to_create_users_throws_for_invalid_username() {
//		whenQueryRequestsThisReturnThat("username", null);
//		
//		BaseController.create(ctx);
//		
//		//throw
//	}
	
	
}
