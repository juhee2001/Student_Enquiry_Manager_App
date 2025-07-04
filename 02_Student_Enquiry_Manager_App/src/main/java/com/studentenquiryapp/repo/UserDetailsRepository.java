package com.studentenquiryapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentenquiryapp.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer>{

	public UserDetails findByEmail(String email);
	
	public UserDetails findByEmailAndPwd(String email,String pwd);
}
