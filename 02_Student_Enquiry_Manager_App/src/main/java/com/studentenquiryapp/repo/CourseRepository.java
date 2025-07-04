package com.studentenquiryapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studentenquiryapp.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
