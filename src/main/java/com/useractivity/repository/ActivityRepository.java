package com.useractivity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.useractivity.domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>  {

	List<Activity> findByTimeBetween(long now, long sevenDaysAgo);

}
