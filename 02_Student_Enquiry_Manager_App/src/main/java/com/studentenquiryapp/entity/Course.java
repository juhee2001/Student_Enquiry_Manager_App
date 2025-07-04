package com.studentenquiryapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="AIT_COURSES")
@Data
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer courseId;
	private String courseName;
}
