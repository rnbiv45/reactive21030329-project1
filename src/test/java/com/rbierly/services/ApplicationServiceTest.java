package com.rbierly.services;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.rbierly.data.ApplicationDao;
import com.rbierly.utils.S3Util;

public class ApplicationServiceTest {
	
	ApplicationDao applicationDao = mock(ApplicationDao.class);
	EmployeeService employeeService = mock(EmployeeService.class);
	S3Util s3util = S3Util.getInstance();
	
	
	@Test
	public void submitApplication_Calls_ApplicationDao() {
		
	}
}
