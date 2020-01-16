package com.useractivity.dto;

import java.util.HashSet;
import java.util.Set;

import com.useractivity.domain.Activity;

public class JsonFileDTO {

	private Long employee_id;
	
	
	Set<Activity> activities=new HashSet<>();
	
	public Long getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}
	public Set<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}
	
}
