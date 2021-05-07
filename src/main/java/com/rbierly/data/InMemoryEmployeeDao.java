package com.rbierly.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import io.javalin.http.NotFoundResponse;

import com.rbierly.beans.Employee;

public class InMemoryEmployeeDao implements EmployeeDao {
	private List<Employee> employees = new ArrayList<Employee>();
	
	@Override
	public void create(Employee employee) {
		employees.add(employee);
	}

	@Override
	public void delete(Integer id) {
		employees.removeIf(obj -> obj.getId().equals(id));
	}

	@Override
	public List<Employee> getMany(List<Integer> ids) {
		return null;
	}

	@Override
	public Employee getById(Integer id) {
		Employee employee = employees.stream()
				.filter(obj -> obj.getId().equals(id))
				.findAny()
				.orElse(null);
		if (employee == null) {
			throw new NotFoundResponse();
		}
		return employee;
	}

	@Override
	public void update(Employee employee, Integer id) {
		System.out.println("Unimplemented");
	}

}
