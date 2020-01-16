package com.useractivity.services.Impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.useractivity.domain.Activity;
import com.useractivity.domain.Employee;
import com.useractivity.dto.ActivityDTO;
import com.useractivity.dto.JsonFileDTO;
import com.useractivity.dto.Report;
import com.useractivity.dto.SevenDaysReport;
import com.useractivity.dto.TodaysReport;
import com.useractivity.enumeration.ActivityNameEnum;
import com.useractivity.repository.ActivityRepository;
import com.useractivity.repository.EmployeeRepository;
import com.useractivity.services.FileReadService;
import com.useractivity.util.JsonUtils;

@Service(value="FileReadService")
public class FileReadServiceImpl implements FileReadService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Override
	public boolean readJsonFile(File folder) {
		 File[] listOfFiles = folder.listFiles();
		    for (int i = 0; i < listOfFiles.length; i++) {
		        if (listOfFiles[i].isFile()) {
		            String fileName = listOfFiles[i].getName();
		            String basename = FilenameUtils.getBaseName(fileName);
		            
		            try {
		                byte[] encoded = Files.readAllBytes(Paths.get(folder.getAbsolutePath()+File.separator + listOfFiles[i].getName()));
		                String str = new String(encoded, StandardCharsets.UTF_8);
		                JsonFileDTO jsonFileDTO = JsonUtils.convertJsonStringToObject(str,
		                		JsonFileDTO.class);
		                if(jsonFileDTO!=null) {
		                	addEployee(jsonFileDTO.getEmployee_id(),jsonFileDTO.getActivities());
		                	addActivity(jsonFileDTO.getActivities(),jsonFileDTO.getEmployee_id());
		                	
		                }
		                System.out.println(jsonFileDTO.getEmployee_id());
		                System.out.println(basename + "=" + str);
		            }  catch (Exception e) {

	                    e.printStackTrace();

	                } 
		        }
		    }
		return false;
	}

	private void addActivity(Set<Activity> activities,Long employee_id) {
		Employee emp=new Employee();
		emp.setId(employee_id);
		for(Activity activity:activities) {
			activity.setEmployee(emp);
			activityRepository.save(activity);
		}
		
	}

	private void addEployee(Long employee_id, Set<Activity> activities) {
		Employee emp=new Employee();
		Optional<Employee> dbEmployee=employeeRepository.findById(employee_id);
//		if(dbEmployee.isPresent()) {
//			activities.addAll(dbEmployee.get().getActivities());
//		}
		//emp.setActivities(activities);
		emp.setId(employee_id);
		employeeRepository.save(emp);
		
	}

	@Override
	public Report getReport() {
		 List<SevenDaysReport> all_employeees_last_7_days_statistics = getSevenDaysRecord();
		 List<TodaysReport> todays_activites=getDailyReport();
		Report report=new Report();
		report.setAll_employeees_last_7_days_statistics(all_employeees_last_7_days_statistics);
		report.setTodays_activites(todays_activites);
		return report;
	}

	private  List<TodaysReport> getDailyReport() {
		 List<TodaysReport> todays_activites=new ArrayList<>();
		Long initialTIme=atStartOfDay( new Date() );
		Long LastTime=atEndOfDay(new Date());
		// List<Activity> activities = activityRepository.findByTimeBetween(initialTIme, LastTime);
		List<Employee> employeeList=employeeRepository.findAllDistinctByActivitiesTimeBetween(initialTIme, LastTime);
		for(Employee employee:employeeList) {
			TodaysReport todaysReport=new TodaysReport();
			List<ActivityDTO> activityDTOList=new ArrayList<>();
			
			todaysReport.setEmployee_id(employee.getId());
		
			for(Activity act:employee.getActivities()) {
				ActivityDTO activityDTO=new ActivityDTO();
				activityDTO.setName(act.getName());
				activityDTO.setTime(act.getTime());
				activityDTOList.add(activityDTO);
			}
			todaysReport.setActivity(activityDTOList);
			todays_activites.add(todaysReport);
		}

		return todays_activites;
	}

	private  List<SevenDaysReport>  getSevenDaysRecord() {
		 List<SevenDaysReport> all_employeees_last_7_days_statisticsList=new ArrayList<>();
		Map<ActivityNameEnum, Long> map = new HashMap<>();
		boolean DESC = false;
		long now = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		long sevenDaysAgo = cal.getTimeInMillis();
		List<Activity> activities = activityRepository.findByTimeBetween(sevenDaysAgo, now);
		if (activities != null && activities.size() > 0) {
			map = activities.stream().collect(Collectors.groupingBy(Activity::getName, Collectors.counting()));
			if (map.size() > 0) {
				map = sortByValue(map, DESC);
			}
		}
		for(Map.Entry<ActivityNameEnum,Long> singelMap:map.entrySet()) {
			SevenDaysReport sevenDaysReport=new SevenDaysReport();
			sevenDaysReport.setActivity_name(singelMap.getKey().toString());
			sevenDaysReport.setOccurrences(singelMap.getValue());
			all_employeees_last_7_days_statisticsList.add(sevenDaysReport);
			
		}
		return all_employeees_last_7_days_statisticsList;
	}

	private static Map<ActivityNameEnum, Long> sortByValue(Map<ActivityNameEnum, Long> unsortMap, final boolean order)
    {
        List<Entry<ActivityNameEnum, Long>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
	
	public static Long atStartOfDay(Date date) {
	    LocalDateTime localDateTime = dateToLocalDateTime(date);
	    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
	    return localDateTimeToTimeStamp(startOfDay);
	}

	public static Long atEndOfDay(Date date) {
	    LocalDateTime localDateTime = dateToLocalDateTime(date);
	    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
	    return localDateTimeToTimeStamp(endOfDay);
	}

	private static LocalDateTime dateToLocalDateTime(Date date) {
	    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	private static Long localDateTimeToTimeStamp(LocalDateTime localDateTime) {
	    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
}
