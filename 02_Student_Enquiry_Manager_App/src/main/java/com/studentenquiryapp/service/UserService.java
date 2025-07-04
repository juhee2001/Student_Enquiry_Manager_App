package com.studentenquiryapp.service;

import com.studentenquiryapp.binding.LoginForm;
import com.studentenquiryapp.binding.SignUpForm;
import com.studentenquiryapp.binding.UnlockForm;

public interface UserService {

	public boolean signUp(SignUpForm form);
	
	public boolean unlock(UnlockForm form);
	
	public String login(LoginForm form);
	
	public boolean forgotPwd(String email);
	
}
