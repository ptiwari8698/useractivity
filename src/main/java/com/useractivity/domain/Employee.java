package com.useractivity.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity
public class Employee {

	@Id
	private Long id;
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="employee")
//    @JoinTable(name = "employee_activity")
//	@JoinColumn(name = "id")
	Set<Activity> activities=new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", activities=" + activities + "]";
	}
	
	
	
	
	
}
