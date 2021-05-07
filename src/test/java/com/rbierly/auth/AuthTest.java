package com.rbierly.auth;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.Test;

import com.rbierly.beans.Employee.EmployeeRole;

import io.javalin.http.Context;

import io.javalin.http.UnauthorizedResponse;

public class AuthTest {
	
	private Context ctx = mock(Context.class);
	
	private void whenCtxHeaderRequestsRespondWith(String request, String response) {
		doReturn(response).when(ctx).header(request);
	}
	
	@Test(expected = UnauthorizedResponse.class)
	public void getEmployeeRoleThrowsUnauthorizedResponseWhenNoAccessToken() {
		whenCtxHeaderRequestsRespondWith("access-token", null);
		Auth.getEmployeeRole(ctx);
		// throw
	}
	
	// TODO: figure out why this test refuses to pass
//	@Test
//	public void getEmployeeRoleReturnsTerminatedWhenNoAccessTokenMatch() {
//		//when(ctx.header("access-token")).thenReturn("123");
//		doReturn("123").when(ctx).header("access-token");
//		assertEquals(any(EmployeeRole.class), Auth.getEmployeeRole(ctx));
//	}
	
	
}
