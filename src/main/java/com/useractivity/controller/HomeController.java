package com.useractivity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.useractivity.dto.Report;
import com.useractivity.services.FileReadService;

import java.io.File;


@RestController
@RequestMapping("/api")
public class HomeController {

	@Autowired
	private  FileReadService fileReadService;
	
	
	
	@RequestMapping("/importData")
	    public ResponseEntity<Boolean> ImportDataFromJsonFile()  {
		File folder = new File("D://EmployeeActivitesToBeProcessed");
		boolean falg=fileReadService.readJsonFile(folder);
		 
		   
	       return ResponseEntity.ok().body(falg);
	    }
	
	@RequestMapping("/viewReport")
    public ResponseEntity<Report> viewReport()  {
	
	Report report=fileReadService.getReport();
	 
	   
       return ResponseEntity.ok().body(report);
    }
}
