package com.rbierly.services;

import java.util.List;

import com.rbierly.beans.Employee;

public interface EmployeeService {
	public void create(Employee employee);
	public void delete(Integer id);
	public Employee getById(Integer id);
	public void update(Employee employee, Integer id);
	public Employee getEmployeeByAccessToken(String token);
	public List<Employee> getMany(Integer offset, Integer endIndex);
}
