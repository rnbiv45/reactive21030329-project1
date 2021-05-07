package com.rbierly.data;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbierly.beans.Application;
import com.rbierly.beans.Application.ApplicationStep;
import com.rbierly.beans.Application.EventType;
import com.rbierly.beans.Application.GradingFormat;
import com.rbierly.utils.Cassandra;

import io.javalin.http.BadRequestResponse;

public class KeyspaceApplicationDao implements ApplicationDao {
	private static final Logger log = LogManager.getLogger(KeyspaceApplicationDao.class);
	private static CqlSession session = Cassandra.getInstance().getSession();

	@Override
	public void create(Application application) {
		log.trace("creating application in database");
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(application);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			throw new BadRequestResponse();
		}
		String query = "insert into application json ?;";
		SimpleStatement simpleStatement = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement boundStatement = session.prepare(simpleStatement).bind(json);
		session.execute(boundStatement);
	}

	@Override
	public void delete(Integer id, Integer employeeId) {
		log.trace("deleting application "+id+"in database");
		String query = "delete from application where id = ? and employeeid = ?;";
		SimpleStatement simpleStatement = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement boundStatement = session.prepare(simpleStatement).bind(id, employeeId);
		session.execute(boundStatement);
	}

	@Override
	public List<Application> getMany(Integer start, Integer end) {
		return null;
	}

	@Override
	public Application getById(Integer id) {
		log.trace("getting application "+id+" from database");
		Application app = null;
		String query = "Select * from application where id = ? ALLOW FILTERING;";
		BoundStatement bound = session.prepare(query).bind(id);
		ResultSet result = session.execute(bound);
		Row data = result.one();
		if (data != null) {

			app = new Application();
		
			app.setId(data.getInt("id"));
			app.setEmployeeId(data.getInt("employeeid"));
			app.setSupervisorId(data.getInt("supervisorid"));
			app.setDepartmentHeadId(data.getInt("departmentheadid"));
			app.setBencoId(data.getInt("bencoid"));
			app.setDateTime(data.getString("datetime"));
			app.setLocation(data.getString("location"));
			app.setDescription(data.getString("description"));
			app.setCost(data.getFloat("cost"));
			app.setAwardAmount(data.getFloat("awardamount"));
			app.setGradingFormat(GradingFormat.valueOf(data.getString("gradingformat")));
			app.setGrade(data.getString("grade"));
			app.setEventType(EventType.valueOf(data.getString("eventtype"))) ;
			app.setJustification(data.getString("justification"));
			app.setAttachments(data.getList("attachments", String.class));
			app.setDeniedBySupervisor(data.getBoolean("deniedbysupervisor"));
			app.setSupervisorDenialReason(data.getString("supervisordenialreason"));
			app.setDeniedByDepartmentHead(data.getBoolean("deniedbydepartmenthead"));
			app.setDepartmentHeadDenialReason(data.getString("departmentheaddenialreason"));
			app.setDeniedByBenco(data.getBoolean("deniedbybenco"));
			app.setBencoDenialReason(data.getString("bencodenialreason"));
			app.setPassingGrade(data.getBoolean("passinggrade"));
			app.setApproved(data.getBoolean("approved"));
			app.setStep(ApplicationStep.valueOf(data.getString("step")));
			app.setExceedsAvailableFunds(data.getBoolean("exceedsavailablefunds"));
			app.setExceedingFundsReason(data.getString("exceedingfundsreason"));
		}
		
		return app;
	}

	@Override
	public void update(Application app, Integer id) {
		log.trace("updating application "+id+" in database");
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(app);
		} catch (JsonProcessingException e) {
			throw new BadRequestResponse();
		}
		String query = "insert into application json ?;";
		SimpleStatement simpleStatement = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement boundStatement = session.prepare(simpleStatement).bind(json);
		session.execute(boundStatement);
	}

	@Override
	public List<Application> getAllByEmployeeId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Application> getAllBySupervisorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
