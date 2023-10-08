package com.mak.consumer.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmpRequset {
	
	@NotBlank(message = "name can not be blank")
	String name;
	
	//@JsonProperty("emp_salary")
	Integer salary;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	String joiningDate;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@JsonProperty("admission_date")
	LocalDateTime admissionDate; 
}
