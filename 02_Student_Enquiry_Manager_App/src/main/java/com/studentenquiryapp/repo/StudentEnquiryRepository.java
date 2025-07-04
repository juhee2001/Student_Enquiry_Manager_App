package com.studentenquiryapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentenquiryapp.entity.StudentEnquiry;

public interface StudentEnquiryRepository extends JpaRepository<StudentEnquiry, Integer> {

}
