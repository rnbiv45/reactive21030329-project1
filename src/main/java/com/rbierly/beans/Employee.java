package com.rbierly.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.javalin.core.security.Role;

public class Employee {
	public enum EmployeeRole implements Serializable, Role { TERMINATED, REGULAR, SUPERVISOR, DEPARTMENT_HEAD, BEN_CO }
	
	private Integer id;
	private Float availableReimbursement;
	private List<String> applications;
	private EmployeeRole role;
	private String email;
	
	public Employee() {
		super();
		this.availableReimbursement = 1000f;
		this.applications = new ArrayList<String>();
		this.role = EmployeeRole.REGULAR;
		this.email = "Default@email.com";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getAvailableReimbursement() {
		return availableReimbursement;
	}

	public void setAvailableReimbursement(Float availableReimbursement) {
		this.availableReimbursement = availableReimbursement;
	}

	public List<String> getApplications() {
		return applications;
	}

	public void setApplications(List<String> applications) {
		this.applications = applications;
	}

	public EmployeeRole getRole() {
		return role;
	}

	public void setRole(EmployeeRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applications == null) ? 0 : applications.hashCode());
		result = prime * result + ((availableReimbursement == null) ? 0 : availableReimbursement.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (applications == null) {
			if (other.applications != null)
				return false;
		} else if (!applications.equals(other.applications))
			return false;
		if (availableReimbursement == null) {
			if (other.availableReimbursement != null)
				return false;
		} else if (!availableReimbursement.equals(other.availableReimbursement))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", availableReimbursement=" + availableReimbursement + ", applications="
				+ applications + ", role=" + role + ", email=" + email + "]";
	}

	
}
