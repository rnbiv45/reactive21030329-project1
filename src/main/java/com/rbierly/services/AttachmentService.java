package com.rbierly.services;

import java.io.InputStream;

import com.rbierly.beans.Employee;

import io.javalin.http.UploadedFile;

public interface AttachmentService {
	public void create(Employee loggedInEmployee, Integer appId, UploadedFile file);
	public void delete(Employee loggedInEmployee, Integer appId, String fileId);
	public InputStream get(Employee loggedInEmployee, Integer appId, String fileId);
}
