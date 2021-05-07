package com.rbierly.controllers;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rbierly.services.AttachmentServiceImp;
import com.rbierly.auth.Auth;
import com.rbierly.services.AttachmentService;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.http.GoneResponse;
import io.javalin.http.UploadedFile;

public class AttachmentController implements CrudHandler {
	private static Logger log = LogManager.getLogger(AttachmentController.class);
	private static AttachmentService attachmentService = new AttachmentServiceImp();

	@Override
	public void create(Context ctx) {
		log.trace("create attachment request");
		Integer applicationId = ctx.formParam("Application-Id", Integer.class).get();
		UploadedFile file = ctx.uploadedFile("file");
		attachmentService.create(Auth.loggedInEmployee(ctx), applicationId, file);
		ctx.status(201);
		ctx.result("File attached");
	}

	@Override
	public void delete(Context ctx, String id) {
		log.trace("delete attachment request");
		Integer applicationId = ctx.formParam("Application-Id", Integer.class).get();
		attachmentService.delete(Auth.loggedInEmployee(ctx), applicationId, id);
		ctx.status(204);
		ctx.result("File deleted");
	}

	@Override
	public void getAll(Context ctx) {
		// TODO Auto-generated method stub
		throw new GoneResponse();
	}

	@Override
	public void getOne(Context ctx, String id) {
		// TODO Auto-generated method stub
		InputStream attachment = attachmentService.get(
				Auth.loggedInEmployee(ctx), 
				ctx.formParam("Application-Id", Integer.class).get(), 
				id);
		ctx.result(attachment);
	}

	@Override
	public void update(Context ctx, String id) {
		// TODO Auto-generated method stub
		throw new GoneResponse();
	}

}
