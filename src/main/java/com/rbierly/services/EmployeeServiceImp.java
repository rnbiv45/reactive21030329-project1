package com.rbierly.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rbierly.beans.Employee;
import com.rbierly.controllers.EmployeeController;
import com.rbierly.data.EmployeeDao;
import com.rbierly.data.InMemoryEmployeeDao;
import com.rbierly.data.KeyspaceEmployeeDao;

public class EmployeeServiceImp implements EmployeeService {
	private static final Logger log = LogManager.getLogger(EmployeeService.class);
	private static EmployeeDao employeeDao = new KeyspaceEmployeeDao();

	@Override
	public Employee getEmployeeByAccessToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Employee employee) {
		log.trace("creating employee "+employee.toString());
		employeeDao.create(employee);
	}

	@Override
	public void delete(Integer id) {
		log.trace("deleting employee "+id.toString());
		employeeDao.delete(id);
	}

	@Override
	public List<Employee> getMany(Integer startIndex, Integer endIndex) {
		List<Employee> employees = new ArrayList<Employee>();
		for (Integer i = startIndex; i <= endIndex; i++) {
			Employee employee = employeeDao.getById(i);
			if (employee != null) {
				employees.add(employee);
			}
		}
		return employees;
	}

	@Override
	public Employee getById(Integer id) {
		log.trace("Get by Id "+id.toString());
		return employeeDao.getById(id);
	}

	@Override
	public void update(Employee employee, Integer id) {
		log.trace("update employee "+id.toString());
		employeeDao.update(employee, id);
	}
}
