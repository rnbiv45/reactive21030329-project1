package com.rbierly.cassandra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

public class CassandraTest {
	
	@Test
	public void canGetScheme() {
		//Use DriverConfigLoader to load your configuration file
        DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
        try (CqlSession session = CqlSession.builder().withConfigLoader(loader).build()) {
            ResultSet rs = session.execute("select * from system_schema.keyspaces");
            Row row = rs.one();
            assertEquals("system_schema", row.getString("keyspace_name"));
        }
	}
}
