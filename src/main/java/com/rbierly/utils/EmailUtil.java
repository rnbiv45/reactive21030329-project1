package com.rbierly.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailUtil {
	private static Logger log = LogManager.getLogger(EmailUtil.class);
	private static String username = System.getenv("EMAIL_USER");
	private static String password = System.getenv("EMAIL_PASS");
	
	
	
	public static void sendEmail(String receiver, String subject, String message) {
		log.trace("Sending email");
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.mailtrap.io");
			email.setSmtpPort(2525);
	        email.setAuthenticator(new DefaultAuthenticator(username, password));
	        email.setStartTLSEnabled(true);
	        email.setFrom(username+"@mailtrap.io");
	        email.setSubject(subject); // subject from HTML-form
	        email.setMsg(message); // message from HTML-form
	        email.addTo(receiver);
	        email.send(); // will throw email-exception if something is wrong
	        log.trace("Email sent to sandbox environment");
		} catch (EmailException e) {
			log.error(e);
		}
	}
	
}
