package com.studentenquiryapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentenquiryapp.entity.EnquiryStatus;

public interface EnquiryStatusRepository extends JpaRepository<EnquiryStatus, Integer>{

}
