package com.useractivity.dto;



import com.useractivity.enumeration.ActivityNameEnum;

public class ActivityDTO {

	

	
	private ActivityNameEnum name;
	
	
	private Long time;


	


	public ActivityNameEnum getName() {
		return name;
	}


	public void setName(ActivityNameEnum name) {
		this.name = name;
	}


	public Long getTime() {
		return time;
	}


	public void setTime(Long time) {
		this.time = time;
	}
	
	
}
