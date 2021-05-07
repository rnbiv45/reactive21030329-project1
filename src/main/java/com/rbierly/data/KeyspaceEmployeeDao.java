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
import com.rbierly.beans.Employee;
import com.rbierly.beans.Employee.EmployeeRole;
import com.rbierly.utils.Cassandra;

public class KeyspaceEmployeeDao implements EmployeeDao {
	private static final Logger log = LogManager.getLogger(KeyspaceApplicationDao.class);
	private CqlSession session = Cassandra.getInstance().getSession();

	@Override
	public void create(Employee employee) {
		log.trace("creating employee in database");
		String query = "insert into employee (id, role, available, applications, email) values (?, ?, ?, ?, ?);";
		SimpleStatement simpleStatement = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement boundStatement = session.prepare(simpleStatement).bind(
				employee.getId(),
				employee.getRole().toString(),
				employee.getAvailableReimbursement(),
				employee.getApplications(),
				employee.getEmail());
		session.execute(boundStatement);
	}

	@Override
	public void delete(Integer id) {
		log.trace("deleting employee in database");
		String query = "delete from employee where id = ?;";
		SimpleStatement simpleStatement = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement boundStatement = session.prepare(simpleStatement).bind(id);
		session.execute(boundStatement);
	}

	@Override
	public List<Employee> getMany(List<Integer> ids) {
		return null;
	}

	@Override
	public Employee getById(Integer id) {
		Employee employee = null;
		String query = "select * from employee where id = ?;";
		SimpleStatement simpleStatment = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement boundStatement = session.prepare(simpleStatment).bind(id);
		ResultSet rs = session.execute(boundStatement);
		Row data = rs.one();
		if (data != null) {
			employee = new Employee();
			employee.setId(data.getInt("id"));
			employee.setRole(EmployeeRole.valueOf(data.getString("role")));
			employee.setApplications(data.getList("applications", String.class));
			employee.setAvailableReimbursement(data.getFloat("available"));
			employee.setEmail(data.getString("email"));
		}
		return employee;
	}

	@Override
	public void update(Employee employee, Integer id) {
		String query = "insert into employee (id, role, available, applications, email) values (?, ?, ?, ?, ?);";
		SimpleStatement simpleStatement = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement boundStatement = session.prepare(simpleStatement).bind(
				employee.getId(),
				employee.getRole().toString(),
				employee.getAvailableReimbursement(),
				employee.getApplications(),
				employee.getEmail());
		session.execute(boundStatement);
	}

}
