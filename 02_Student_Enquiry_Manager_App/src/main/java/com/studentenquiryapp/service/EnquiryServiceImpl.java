package com.studentenquiryapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentenquiryapp.binding.DashboardResponseForm;
import com.studentenquiryapp.binding.EnquiryForm;
import com.studentenquiryapp.binding.EnquirySearchCriteriaForm;
import com.studentenquiryapp.entity.Course;
import com.studentenquiryapp.entity.EnquiryStatus;
import com.studentenquiryapp.entity.StudentEnquiry;
import com.studentenquiryapp.entity.UserDetails;
import com.studentenquiryapp.repo.CourseRepository;
import com.studentenquiryapp.repo.EnquiryStatusRepository;
import com.studentenquiryapp.repo.StudentEnquiryRepository;
import com.studentenquiryapp.repo.UserDetailsRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService{

	@Autowired
	private UserDetailsRepository userDetailsRepo;
	
	@Autowired
	private CourseRepository coursesRepo;
	
	@Autowired
	private EnquiryStatusRepository statusRepo;
	
	@Autowired
	private StudentEnquiryRepository enqRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public DashboardResponseForm getDashboardData(Integer userId) {
		
		DashboardResponseForm response=new DashboardResponseForm();
		
		Optional<UserDetails> findById=userDetailsRepo.findById(userId); 
		if(findById.isPresent()) {
			UserDetails user=findById.get();
			List<StudentEnquiry> enquiries=user.getEnquiries();
			Integer totalCnt=enquiries.size();
			
			Integer enrolledCnt=enquiries.stream()
			        .filter(e -> e.getEnqStatus().equals("ENROLLED"))
			        .collect(Collectors.toList()).size();
			
			Integer lostCnt=enquiries.stream()
			        .filter(e -> e.getEnqStatus().equals("LOST"))
			        .collect(Collectors.toList()).size();
			
			response.setTotalEnquriesCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
			
		}
		return response;
	}

	@Override
	public List<String> getCourseName() {
		List<Course> findAll=coursesRepo.findAll();
		List<String> names=new ArrayList<String>();
		for(Course entity:findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnquiryStatus> findAll=statusRepo.findAll();
		List<String> status=new ArrayList<String>();
		for(EnquiryStatus entity:findAll) {
			status.add(entity.getStatusName());
		}
		return status;
	}


	@Override
	public boolean saveEnquiry(EnquiryForm form) {
	    StudentEnquiry enqEntity = new StudentEnquiry();

	    // If it's an existing enquiry (edit case), fetch the original record first
	    if (form.getEnqId() != null) {
	        Optional<StudentEnquiry> optional = enqRepo.findById(form.getEnqId());
	        if (optional.isPresent()) {
	            enqEntity = optional.get(); // update existing
	        }
	    }

	    // Copy updated fields from form
	    BeanUtils.copyProperties(form, enqEntity);

	    // Always set the user (in case it's a new enquiry)
	    Integer userId = (Integer) session.getAttribute("userId");
	    UserDetails userEntity = userDetailsRepo.findById(userId).get();
	    enqEntity.setUser(userEntity);

	    enqRepo.save(enqEntity);
	    return true;
	}

	@Override
	public List<StudentEnquiry> getEnquiries() {
		Integer userId=(Integer)session.getAttribute("userId");
		Optional<UserDetails> findById=userDetailsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetails userDtlsEntity=findById.get();
			List<StudentEnquiry> enquiries=userDtlsEntity.getEnquiries();
			return enquiries;
		}
		return null;
	}
	
	@Override
	public List<StudentEnquiry> getFilterEnq(EnquirySearchCriteriaForm criteria, Integer userId) {
		Optional<UserDetails> findById=userDetailsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetails userDtlsEntity=findById.get();
			List<StudentEnquiry> enquiries=userDtlsEntity.getEnquiries();
			
			//filter logic
			
			if(null != criteria.getCourseName() && !"".equals(criteria.getCourseName())) {
			enquiries = enquiries.stream()
				.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
				.collect(Collectors.toList());
			}
			
			if(null != criteria.getEnqStatus() && !"".equals(criteria.getEnqStatus())) {
				enquiries = enquiries.stream()
					.filter(e -> e.getEnqStatus().equals(criteria.getEnqStatus()))
					.collect(Collectors.toList());
			}
			
			if(null != criteria.getClassMode() && !"".equals(criteria.getClassMode())) {
				enquiries = enquiries.stream()
					.filter(e -> e.getClassMode().equals(criteria.getClassMode()))
					.collect(Collectors.toList());
			}
			
			return enquiries;
		}
		
		return null;
	}

	
	
	@Override
	public StudentEnquiry getEnquiryById(Integer enqId) {
	    return enqRepo.findById(enqId).orElse(null);
	}



	

	

	
}
