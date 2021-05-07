package com.rbierly.utils;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3Util {
	public static final Region region = Region.US_EAST_1;
	public static final String BUCKET_NAME = "project-1-trms";
	
	private static Logger log = LogManager.getLogger(S3Util.class);	
	private S3Client s3 = null;
	
	private S3Util() {
		s3 = S3Client.builder().region(region).build();
	}
	
	private static class S3UtilHolder {
		public static final S3Util instance = new S3Util();
	}
	
	public static S3Util getInstance() {
		return S3UtilHolder.instance;
	}
	
	public void uploadToBucket(String key, byte[] file) {
		log.trace("Uploading file as "+key);
		s3.putObject(PutObjectRequest.builder().bucket(BUCKET_NAME).key(key)
                .build(),
                RequestBody.fromBytes(file));
		
		log.trace("Upload Complete");
	}
	
	public InputStream getObject(String key) {
		log.trace("Retrieving Data from S3: "+key);
		InputStream s = s3.getObject(GetObjectRequest.builder().bucket(BUCKET_NAME).key(key).build());
		return s;
	}
	
	
}