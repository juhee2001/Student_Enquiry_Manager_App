package com.studentenquiryapp.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentenquiryapp.binding.DashboardResponseForm;
import com.studentenquiryapp.binding.EnquiryForm;
import com.studentenquiryapp.binding.EnquirySearchCriteriaForm;
import com.studentenquiryapp.entity.StudentEnquiry;
import com.studentenquiryapp.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService service;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/logout")
	public String logoutPage() {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		//TODO:logic to fetch data for dashboard
		
		Integer userId=(Integer)session.getAttribute("userId");
		
		DashboardResponseForm dashboardData=service.getDashboardData(userId);
		
		model.addAttribute("dashboardData", dashboardData);
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
		//get courses for drop down
		List<String> courseNames=service.getCourseName();
		
		//get enq status for drop down
		List<String> statuses=service.getEnqStatus();
		
		//create binding class obj
		EnquiryForm formObj=new EnquiryForm();
		
		//set data in model obj
		model.addAttribute("courseNames", courseNames);
		model.addAttribute("statusList", statuses);
		model.addAttribute("formObj", formObj);
		
		return "add-enquiry";
	}
	
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
	    boolean status = service.saveEnquiry(formObj);

	    if (status) {
	        // Check if this was an edit
	        if (formObj.getEnqId() != null) {
	            model.addAttribute("succMsg", "Enquiry Updated Successfully");
	        } else {
	            model.addAttribute("succMsg", "Enquiry Added Successfully");
	        }
	    } else {
	        model.addAttribute("errMsg", "Problem Occurred");
	    }

	    // Reload dropdowns for form
	    List<String> courseNames = service.getCourseName();
	    List<String> statusList = service.getEnqStatus();
	    model.addAttribute("courseNames", courseNames);
	    model.addAttribute("statusList", statusList);

	    return "add-enquiry";
	}

	
	private void initForm(Model model) {
		
		//get courses for drop down
		List<String> courses=service.getCourseName();
		
		//get enq status for drop down
		List<String> enqStatuses=service.getEnqStatus();
		
		//create binding class obj
		EnquiryForm formObj=new EnquiryForm();
		
		//set data in model obj
		model.addAttribute("coursesNames", courses);
		model.addAttribute("statusNames",enqStatuses);
		model.addAttribute("formObj",formObj);
		
	}
	
	@GetMapping("/enquires")
	public String viewEnquiriesPage(Model model) {
		initForm(model);
		model.addAttribute("searchForm", new EnquirySearchCriteriaForm());
		List<StudentEnquiry> enquiries=service.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		
		return "view-enquiries";
	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname, 
			@RequestParam String status, @RequestParam String mode, Model model) {
		
		EnquirySearchCriteriaForm criteria=new EnquirySearchCriteriaForm();
		
		criteria.setCourseName(cname);
		criteria.setEnqStatus(status);
		criteria.setClassMode(mode);
		
		Integer userId=(Integer)session.getAttribute("userId");
		List<StudentEnquiry> filteredEnquiries=service.getFilterEnq(criteria, userId);
		
		model.addAttribute("enquiries",filteredEnquiries);
		return "filter-enquiries-page";
	}
	
	@GetMapping("/edit")
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model) {
	    StudentEnquiry enquiry = service.getEnquiryById(enqId);

	    if (enquiry == null) {
	        return "redirect:/enquires"; 
	    }

	    // Convert entity to form object
	    EnquiryForm form = new EnquiryForm();
	    BeanUtils.copyProperties(enquiry, form);

	    // Fetch dropdown values
	    List<String> courseNames = service.getCourseName();
	    List<String> statusList = service.getEnqStatus();

	    model.addAttribute("formObj", form);
	    model.addAttribute("courseNames", courseNames);
	    model.addAttribute("statusList", statusList);

	    return "add-enquiry"; 
	}


}
