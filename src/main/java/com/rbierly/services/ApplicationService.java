package com.rbierly.services;

import java.util.List;

import com.rbierly.beans.Application;
import com.rbierly.beans.Employee;

import io.javalin.http.UploadedFile;

public interface ApplicationService {
	public void submitApplication(Employee loggedInEmployee, Application application, List<UploadedFile> attachments);
	public void deleteById(Employee loggedInEmployee, Integer integer);
	public List<Application> getMany(Integer offset, Integer i);
	public Application getById(Employee loggedInEmployee, Integer validInt);
	public List<Application> getAllByEmployeeId(Integer id);
	public List<Application> getAllBySupervisorId(Integer id);
	public void update(Application application, Integer validInt);
	public void approval(Integer id, Employee loggedInEmployee, Boolean denied, String reason);
	public void updateAward(Employee loggedInEmployee, Application app, Float award, String reason);
}
