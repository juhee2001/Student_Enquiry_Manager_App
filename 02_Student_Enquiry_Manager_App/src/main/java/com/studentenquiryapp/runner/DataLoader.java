package com.studentenquiryapp.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.studentenquiryapp.entity.Course;
import com.studentenquiryapp.entity.EnquiryStatus;
import com.studentenquiryapp.repo.CourseRepository;
import com.studentenquiryapp.repo.EnquiryStatusRepository;

@Component
public class DataLoader implements ApplicationRunner{

	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private EnquiryStatusRepository statusRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		
		Course c1=new Course();
		c1.setCourseName("Java");
		
		Course c2=new Course();
		c2.setCourseName("Python");
		
		Course c3=new Course();
		c3.setCourseName("DevOps");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		statusRepo.deleteAll();
		
		EnquiryStatus e1=new EnquiryStatus();
		e1.setStatusName("NEW");
		
		EnquiryStatus e2=new EnquiryStatus();
		e2.setStatusName("ENROLLED");
		
		EnquiryStatus e3=new EnquiryStatus();
		e3.setStatusName("LOST");
		
		statusRepo.saveAll(Arrays.asList(e1,e2,e3));
		
	}
	

	
}
