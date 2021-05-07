package com.rbierly.data;

import java.util.List;

import com.rbierly.beans.Application;

public interface ApplicationDao {
	public void create(Application application);
	public void delete(Integer id, Integer employeeId);
	public List<Application> getMany(Integer start, Integer end);
	public Application getById(Integer id);
	public void update(Application application, Integer id);
	public List<Application> getAllByEmployeeId(Integer id);
	public List<Application> getAllBySupervisorId(Integer id);
}
