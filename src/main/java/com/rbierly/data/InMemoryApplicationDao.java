package com.rbierly.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rbierly.beans.Application;

public class InMemoryApplicationDao implements ApplicationDao {
	private List<Application> applications = new ArrayList<Application>();

	@Override
	public void create(Application application) {
		applications.add(application);
	}

	@Override
	public List<Application> getMany(Integer start, Integer end) {
		return applications.stream()
				.filter(obj -> start <= obj.getId() && obj.getId() <= end)
				.collect(Collectors.toList());
	}

	@Override
	public Application getById(Integer id) {
		return applications.stream()
				.filter(obj -> obj.getId().equals(id))
				.findAny()
				.orElse(null);
	}

	@Override
	public void update(Application application, Integer id) {
		System.out.println("Unimplemented");
	}

	@Override
	public List<Application> getAllByEmployeeId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Application> getAllBySupervisorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id, Integer employeeId) {
		// TODO Auto-generated method stub
		
	}

}
