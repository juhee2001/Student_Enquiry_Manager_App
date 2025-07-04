package com.studentenquiryapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.studentenquiryapp.binding.LoginForm;
import com.studentenquiryapp.binding.SignUpForm;
import com.studentenquiryapp.binding.UnlockForm;
import com.studentenquiryapp.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean status=userService.signUp(form);
		if(status) {
			model.addAttribute("succMsg", "Account Created,Check Your Email");
		}else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}
		return "signup";
	}
	
	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		
		UnlockForm unlockFormObj=new UnlockForm();
		unlockFormObj.setEmail(email);
		
		model.addAttribute("unlock", unlockFormObj);
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockAccountPage(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		
		if(unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status=userService.unlock(unlock);
			if(status) {
				model.addAttribute("succMsg", "Your Account Unlocked Successfully");
				
			}else {
				model.addAttribute("errMsg", "Given Temporary Password is Incorrect,Check your email");
				
			}
		}else {
			model.addAttribute("errMsg", "New Pwd and Confirm Pwd should be Same");
			
		}
		
		
		return "unlock";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm,Model model) {
		
		String status=userService.login(loginForm);
		if(status.contains("success")) {
			//redirect request to dashboard method
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status);
		return "login";
	}
	
	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotpwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email,Model model) {
		
		//TODO: Logic
		
		boolean status=userService.forgotPwd(email);
		if(status) {
			//send success msg
			model.addAttribute("succMsg", "Pwd sent to your email");
		}else {
			//send error msg
			model.addAttribute("errMsg", "Invalid email");
		}
		return "forgotpwd";
	}
}
