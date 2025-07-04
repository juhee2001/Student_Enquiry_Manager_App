package com.studentenquiryapp.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="AIT_STUDENT_ENQURIES")
@Entity
public class StudentEnquiry {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer enqId;
	
	private String studentName;
	
	private Long studentPhno;
	
	private String classMode;
	
	private String courseName;
	
	private String enqStatus;
	
	@CreationTimestamp
	private LocalDate dateCreated;
	
	@CreationTimestamp
	private LocalDate lastUpdated;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetails user;
}
