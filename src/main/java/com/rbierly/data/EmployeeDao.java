package com.rbierly.data;

import java.util.List;

import com.rbierly.beans.Employee;

public interface EmployeeDao {
	public void create(Employee employee);
	public void delete(Integer id);
	public Employee getById(Integer id);
	public void update(Employee employee, Integer id);
	List<Employee> getMany(List<Integer> ids);
}
