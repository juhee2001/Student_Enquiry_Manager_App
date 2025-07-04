package com.studentenquiryapp.service;

import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentenquiryapp.binding.LoginForm;
import com.studentenquiryapp.binding.SignUpForm;
import com.studentenquiryapp.binding.UnlockForm;

import com.studentenquiryapp.entity.UserDetails;
import com.studentenquiryapp.repo.UserDetailsRepository;
import com.studentenquiryapp.util.EmailUtil;
import com.studentenquiryapp.util.PwdUtil;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDetailsRepository userDetailsRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public boolean signUp(SignUpForm form) {
		
		UserDetails user=userDetailsRepo.findByEmail(form.getEmail());
		
		if(user != null) {
			return false;
		}
		
		//TODO: copy data from binding object to entity object
		UserDetails entity=new UserDetails();
		BeanUtils.copyProperties(form, entity);
		
		//TODO: generate random pwd and set to object
		String tempPwd=PwdUtil.generateRandomPwd();
		entity.setPwd(tempPwd);
		
		//TODO: Set account status as LOCKED
		entity.setAccStatus("LOCKED");
		
		
		//TODO: Insert record
		userDetailsRepo.save(entity);
		
		//TODO: Send email to user to unlock the account
		String to=form.getEmail();
		String subject="Unlock Your Account | Ashok IT";
		
		StringBuffer body=new StringBuffer();
		body.append("<h1>Use below temporary password to unlock your account</h1>");
		body.append("Temporary Pwd:"+tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\")>Click Here To Unlock Your Account</a>");
		
		emailUtil.sendEmail(to, subject, body.toString());
		return true;
	}
	

	@Override
	public boolean unlock(UnlockForm form) {
		UserDetails user=userDetailsRepo.findByEmail(form.getEmail());
		
		if(user.getPwd().equals(form.getTempPwd())) {
			user.setPwd(form.getNewPwd());
			user.setAccStatus("UNLOCKED");
			
			userDetailsRepo.save(user);
			return true;
			
		}else {
			return false;
		}
		
	}

	@Override
	public String login(LoginForm form) {
		UserDetails user=userDetailsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		
		if(user == null) {
		    return "Invalid Credentials";
		}
		if(user.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
		
		//create session and store user data in session
		session.setAttribute("userId", user.getUserId());
		return "success";
	}
	
	@Override
	public boolean forgotPwd(String email) {
		
		//check record presence in db with given email
		UserDetails user=userDetailsRepo.findByEmail(email);
		
		//if record not available send return false
		if(user==null) {
			return false;
		}
		
		//if record is available send pwd to email and return true
		
		String subject="Recover Password";
		String body="Your Password ::"+user.getPwd();
		emailUtil.sendEmail(email, subject, body);
		return true;
	}

}
