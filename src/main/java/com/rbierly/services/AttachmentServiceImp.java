package com.rbierly.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.rbierly.beans.Application;
import com.rbierly.beans.Employee;
import com.rbierly.utils.S3Util;

import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.UploadedFile;

public class AttachmentServiceImp implements AttachmentService {
	private static ApplicationService applicationService = new ApplicationServiceImp();
	private static S3Util s3util = S3Util.getInstance();

	@Override
	public void create(Employee loggedInEmployee, Integer appId, UploadedFile file) {
		Application app = applicationService.getById(loggedInEmployee, appId);
		List<String> attachments = app.getAttachments();
		attachments.add(file.getFilename());
		app.setAttachments(attachments);
		try {
			s3util.uploadToBucket(appId.toString()+file.getFilename(), file.getContent().readAllBytes());
		} catch (IOException e) {
			throw new InternalServerErrorResponse("File could not be saved");
		}
		applicationService.update(app, appId);
	}

	@Override
	public void delete(Employee loggedInEmployee, Integer appId, String fileId) {
		Application app = applicationService.getById(loggedInEmployee, appId);
		List<String> attachments = app.getAttachments();
		attachments.remove(fileId);
		app.setAttachments(attachments);
		applicationService.update(app, appId);
	}

	@Override
	public InputStream get(Employee loggedInEmployee, Integer appId, String fileId) {
		InputStream result = null;
		Application app = applicationService.getById(loggedInEmployee, appId);
		try {
			result = s3util.getObject(app.getId().toString()+fileId);
		} catch (Exception e) {
			throw new InternalServerErrorResponse("Resource could not be loaded.");
		}
		return result;
	}

}
