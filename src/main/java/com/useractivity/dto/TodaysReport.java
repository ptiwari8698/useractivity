package com.useractivity.dto;

import java.util.ArrayList;
import java.util.List;

import com.useractivity.domain.Activity;

public class TodaysReport {
	
	private Long employee_id;
	List<ActivityDTO> activity=new ArrayList<>();
	public Long getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}
	public List<ActivityDTO> getActivity() {
		return activity;
	}
	public void setActivity(List<ActivityDTO> activity) {
		this.activity = activity;
	}
	
	
}
