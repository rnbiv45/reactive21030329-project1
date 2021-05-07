package com.rbierly.beans;

import java.util.List;

public class Application {
	public static enum EventType { 
		UNIVERSITY_COURSE (0.8), 
		SEMINAR (0.6), 
		CERTIFICATION_PREPARATION_CLASS (0.75), 
		CERTIFICATION (1.0), 
		TECHNICAL_TRAINING (0.9), 
		OTHER (0.3);
		
		private double percent;
		EventType (double percent) {
			this.percent = percent;
		}
		public double getPercent() {
			return percent;
		}
 	}
	public enum GradingFormat { LETTER, PASS_FAIL, OUT_OF_TEN }
	public enum ApplicationStep {SUPERVISOR, DEPT_HEAD, BEN_CO, PENDING, APPROVED, DENIED}
	
	private Integer id;
	private Integer employeeId;
	private Integer supervisorId;
	private Integer departmentHeadId;
	private Integer BencoId;
	private String dateTime;
	private String location;
	private String description; 
	private Float cost;
	private Float awardAmount; 
	private GradingFormat gradingFormat;
	private String grade;
	private EventType eventType;
	private String justification;
	private List<String> attachments;
	private Boolean deniedBySupervisor;
	private String supervisorDenialReason;
	private Boolean deniedByDepartmentHead;
	private String departmentHeadDenialReason;
	private Boolean deniedByBenco;
	private String bencoDenialReason;
	private Boolean passingGrade;
	private Boolean approved;
	private ApplicationStep step;
	private Boolean exceedsAvailableFunds;
	private String exceedingFundsReason;
	
	public Application() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public Integer getDepartmentHeadId() {
		return departmentHeadId;
	}

	public void setDepartmentHeadId(Integer departmentHeadId) {
		this.departmentHeadId = departmentHeadId;
	}
	
	public Integer getBencoId() {
		return BencoId;
	}

	public void setBencoId(Integer bencoId) {
		BencoId = bencoId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public Float getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(Float awardAmount) {
		this.awardAmount = awardAmount;
	}

	public GradingFormat getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(GradingFormat gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}

	public Boolean getDeniedBySupervisor() {
		return deniedBySupervisor;
	}

	public void setDeniedBySupervisor(Boolean deniedBySuperVisor) {
		this.deniedBySupervisor = deniedBySuperVisor;
	}

	public String getSupervisorDenialReason() {
		return supervisorDenialReason;
	}

	public void setSupervisorDenialReason(String supervisorDenialReason) {
		this.supervisorDenialReason = supervisorDenialReason;
	}

	public Boolean getDeniedByDepartmentHead() {
		return deniedByDepartmentHead;
	}

	public void setDeniedByDepartmentHead(Boolean deniedByDepartmentHead) {
		this.deniedByDepartmentHead = deniedByDepartmentHead;
	}

	public String getDepartmentHeadDenialReason() {
		return departmentHeadDenialReason;
	}

	public void setDepartmentHeadDenialReason(String departmentHeadDenialReason) {
		this.departmentHeadDenialReason = departmentHeadDenialReason;
	}

	public Boolean getDeniedByBenco() {
		return deniedByBenco;
	}

	public void setDeniedByBenco(Boolean deniedByBenco) {
		this.deniedByBenco = deniedByBenco;
	}

	public String getBencoDenialReason() {
		return bencoDenialReason;
	}

	public void setBencoDenialReason(String bencoDenialReason) {
		this.bencoDenialReason = bencoDenialReason;
	}

	public Boolean getPassingGrade() {
		return passingGrade;
	}

	public void setPassingGrade(Boolean passingGrade) {
		this.passingGrade = passingGrade;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public ApplicationStep getStep() {
		return step;
	}

	public void setStep(ApplicationStep step) {
		this.step = step;
	}
	
	public Boolean getExceedsAvailableFunds() {
		return exceedsAvailableFunds;
	}

	public void setExceedsAvailableFunds(Boolean exceedsAvailableFunds) {
		this.exceedsAvailableFunds = exceedsAvailableFunds;
	}

	public String getExceedingFundsReason() {
		return exceedingFundsReason;
	}

	public void setExceedingFundsReason(String exceedingFundsReason) {
		this.exceedingFundsReason = exceedingFundsReason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BencoId == null) ? 0 : BencoId.hashCode());
		result = prime * result + ((approved == null) ? 0 : approved.hashCode());
		result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
		result = prime * result + ((awardAmount == null) ? 0 : awardAmount.hashCode());
		result = prime * result + ((bencoDenialReason == null) ? 0 : bencoDenialReason.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((deniedByBenco == null) ? 0 : deniedByBenco.hashCode());
		result = prime * result + ((deniedByDepartmentHead == null) ? 0 : deniedByDepartmentHead.hashCode());
		result = prime * result + ((deniedBySupervisor == null) ? 0 : deniedBySupervisor.hashCode());
		result = prime * result + ((departmentHeadDenialReason == null) ? 0 : departmentHeadDenialReason.hashCode());
		result = prime * result + ((departmentHeadId == null) ? 0 : departmentHeadId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + ((exceedingFundsReason == null) ? 0 : exceedingFundsReason.hashCode());
		result = prime * result + ((exceedsAvailableFunds == null) ? 0 : exceedsAvailableFunds.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((gradingFormat == null) ? 0 : gradingFormat.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((justification == null) ? 0 : justification.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((passingGrade == null) ? 0 : passingGrade.hashCode());
		result = prime * result + ((step == null) ? 0 : step.hashCode());
		result = prime * result + ((supervisorDenialReason == null) ? 0 : supervisorDenialReason.hashCode());
		result = prime * result + ((supervisorId == null) ? 0 : supervisorId.hashCode());
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
		Application other = (Application) obj;
		if (BencoId == null) {
			if (other.BencoId != null)
				return false;
		} else if (!BencoId.equals(other.BencoId))
			return false;
		if (approved == null) {
			if (other.approved != null)
				return false;
		} else if (!approved.equals(other.approved))
			return false;
		if (attachments == null) {
			if (other.attachments != null)
				return false;
		} else if (!attachments.equals(other.attachments))
			return false;
		if (awardAmount == null) {
			if (other.awardAmount != null)
				return false;
		} else if (!awardAmount.equals(other.awardAmount))
			return false;
		if (bencoDenialReason == null) {
			if (other.bencoDenialReason != null)
				return false;
		} else if (!bencoDenialReason.equals(other.bencoDenialReason))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (deniedByBenco == null) {
			if (other.deniedByBenco != null)
				return false;
		} else if (!deniedByBenco.equals(other.deniedByBenco))
			return false;
		if (deniedByDepartmentHead == null) {
			if (other.deniedByDepartmentHead != null)
				return false;
		} else if (!deniedByDepartmentHead.equals(other.deniedByDepartmentHead))
			return false;
		if (deniedBySupervisor == null) {
			if (other.deniedBySupervisor != null)
				return false;
		} else if (!deniedBySupervisor.equals(other.deniedBySupervisor))
			return false;
		if (departmentHeadDenialReason == null) {
			if (other.departmentHeadDenialReason != null)
				return false;
		} else if (!departmentHeadDenialReason.equals(other.departmentHeadDenialReason))
			return false;
		if (departmentHeadId == null) {
			if (other.departmentHeadId != null)
				return false;
		} else if (!departmentHeadId.equals(other.departmentHeadId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (eventType != other.eventType)
			return false;
		if (exceedingFundsReason == null) {
			if (other.exceedingFundsReason != null)
				return false;
		} else if (!exceedingFundsReason.equals(other.exceedingFundsReason))
			return false;
		if (exceedsAvailableFunds == null) {
			if (other.exceedsAvailableFunds != null)
				return false;
		} else if (!exceedsAvailableFunds.equals(other.exceedsAvailableFunds))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (gradingFormat != other.gradingFormat)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (justification == null) {
			if (other.justification != null)
				return false;
		} else if (!justification.equals(other.justification))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (passingGrade == null) {
			if (other.passingGrade != null)
				return false;
		} else if (!passingGrade.equals(other.passingGrade))
			return false;
		if (step != other.step)
			return false;
		if (supervisorDenialReason == null) {
			if (other.supervisorDenialReason != null)
				return false;
		} else if (!supervisorDenialReason.equals(other.supervisorDenialReason))
			return false;
		if (supervisorId == null) {
			if (other.supervisorId != null)
				return false;
		} else if (!supervisorId.equals(other.supervisorId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", employeeId=" + employeeId + ", supervisorId=" + supervisorId
				+ ", departmentHeadId=" + departmentHeadId + ", BencoId=" + BencoId + ", dateTime=" + dateTime
				+ ", location=" + location + ", description=" + description + ", cost=" + cost + ", awardAmount="
				+ awardAmount + ", gradingFormat=" + gradingFormat + ", grade=" + grade + ", eventType=" + eventType
				+ ", justification=" + justification + ", attachments=" + attachments + ", deniedBySupervisor="
				+ deniedBySupervisor + ", supervisorDenialReason=" + supervisorDenialReason
				+ ", deniedByDepartmentHead=" + deniedByDepartmentHead + ", departmentHeadDenialReason="
				+ departmentHeadDenialReason + ", deniedByBenco=" + deniedByBenco + ", bencoDenialReason="
				+ bencoDenialReason + ", passingGrade=" + passingGrade + ", approved=" + approved + ", step=" + step
				+ ", exceedsAvailableFunds=" + exceedsAvailableFunds + ", exceedingFundsReason=" + exceedingFundsReason
				+ "]";
	}

}
