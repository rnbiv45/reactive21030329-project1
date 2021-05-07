package com.rbierly.utils;

import io.javalin.core.validation.BodyValidator;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

public class InputValidation {
	public static void idNotNull(String id) {
		if (id == null) {
			throw new BadRequestResponse();
		}
	}
	
	public static void contextBodyNullorEmpty(Context ctx) {
		if (ctx.body() == null || ctx.body().length() == 0) {
			throw new BadRequestResponse("Context body is empty.");
		}
	}
	
	public static <T> void jsonIsValid(BodyValidator<T> bodyValidator) {
		if (!bodyValidator.isValid()) {
			throw new BadRequestResponse("Invalid JSON body.");
		}
	}
	
	public static Integer validInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new BadRequestResponse("Id is not valid");
		}
	}
	
	public static Integer queryParamForInt(Context ctx, String param, Integer defaultValue) {
		String response = ctx.queryParam(param);
		if (response == null || response == "") {
			return defaultValue;
		}
		return validInt(response);
	}
}
