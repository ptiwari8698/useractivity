package com.useractivity.services;

import java.io.File;

import org.springframework.stereotype.Service;

import com.useractivity.dto.Report;


public interface FileReadService {

	boolean readJsonFile(File folder);

	Report getReport();

}
