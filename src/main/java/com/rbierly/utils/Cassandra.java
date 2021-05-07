package com.rbierly.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;


public class Cassandra {
	private static final Logger log = LogManager.getLogger(Cassandra.class);
	private CqlSession session = null;
	
	private Cassandra() {
		log.trace("Establishing connection with Cassandra");
		DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
		try {
			this.session = CqlSession.builder().withConfigLoader(loader).withKeyspace("trms").build();
			log.trace("Session createad");
		} catch (Exception e) {
			log.error("Method threw exception: "+e);
			for(StackTraceElement s : e.getStackTrace()) {
				log.warn(s);
			}
			throw e;
		}
	}
	
	private static class CassandraHolder {
		public static final Cassandra instance = new Cassandra();
	}
	
	public static Cassandra getInstance() {
		return CassandraHolder.instance;
	}
	
	public CqlSession getSession() {
		return this.session;
	}

}
