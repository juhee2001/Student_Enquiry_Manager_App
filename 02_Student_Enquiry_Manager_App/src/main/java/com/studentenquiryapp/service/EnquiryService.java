package com.studentenquiryapp.service;

import java.util.List;

import com.studentenquiryapp.binding.DashboardResponseForm;
import com.studentenquiryapp.binding.EnquiryForm;
import com.studentenquiryapp.binding.EnquirySearchCriteriaForm;
import com.studentenquiryapp.entity.StudentEnquiry;

public interface EnquiryService {

	public DashboardResponseForm getDashboardData(Integer userId);

	public List<String> getCourseName();

	public List<String> getEnqStatus();

	public boolean saveEnquiry(EnquiryForm form);
	
	public List<StudentEnquiry> getEnquiries();

	public List<StudentEnquiry> getFilterEnq(EnquirySearchCriteriaForm criteria,Integer userId);

	public StudentEnquiry getEnquiryById(Integer enqId);

	
	
}
