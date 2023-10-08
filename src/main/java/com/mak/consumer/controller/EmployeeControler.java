package com.mak.consumer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mak.consumer.model.EmpRequset;
import com.mak.consumer.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/service/rest/employee")
@Slf4j
public class EmployeeControler {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping
	public ResponseEntity<String> saveEmployee(@Valid @RequestBody EmpRequset request){
		log.info("Consumer call..");
		String empId= employeeService.saveEmployee(request);
		log.info("consumer saved employee with empid {} ",empId);
		return new ResponseEntity<>("saved employee with empid "+ empId,HttpStatus.OK);
	} 
	
	@GetMapping
	public ResponseEntity<Integer> getAllEmployee(){
		log.info("getAllEmployee");
		return new ResponseEntity<>(employeeService.getAllAvalEmpList(),HttpStatus.OK);
	} 
}
