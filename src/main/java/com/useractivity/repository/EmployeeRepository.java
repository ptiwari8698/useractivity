package com.useractivity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.useractivity.domain.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

	List<Employee> findAllByActivitiesTimeBetween(Long initialTIme, Long lastTime);

	List<Employee> findAllDistinctByActivitiesTimeBetween(Long initialTIme, Long lastTime);

}
