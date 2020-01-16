package com.useractivity.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.useractivity.enumeration.ActivityNameEnum;

public class Report {

	 
	 List<SevenDaysReport> all_employeees_last_7_days_statistics=new ArrayList<>();
	 
	 List<TodaysReport> todays_activites=new ArrayList<>();

	 

	public List<SevenDaysReport> getAll_employeees_last_7_days_statistics() {
		return all_employeees_last_7_days_statistics;
	}

	public void setAll_employeees_last_7_days_statistics(List<SevenDaysReport> all_employeees_last_7_days_statistics) {
		this.all_employeees_last_7_days_statistics = all_employeees_last_7_days_statistics;
	}

	public List<TodaysReport> getTodays_activites() {
		return todays_activites;
	}

	public void setTodays_activites(List<TodaysReport> todays_activites) {
		this.todays_activites = todays_activites;
	}
	 
	 
}
