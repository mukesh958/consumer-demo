package com.mak.consumer.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mak.consumer.dao.EmployeeServiceDao;
import com.mak.consumer.model.Emp;

@RestController
@RequestMapping("/v1/rest/resiliance")
public class ResilianceTestController {
	
	@Autowired
	private EmployeeServiceDao employeeServiceDao;
	
	@GetMapping("/{id}")
	public ResponseEntity<Emp> getEmployeeById(Integer id) throws InterruptedException, ExecutionException {
		return new ResponseEntity<Emp>(employeeServiceDao.getEmployeeById(id), HttpStatus.OK);
	}
	@GetMapping("/async/{id}")
	public ResponseEntity<?> getEmployeeByIdAsysn(Integer id) throws InterruptedException, ExecutionException {
		CompletableFuture.runAsync(() -> {
			employeeServiceDao.getEmployeeByIdAsync(id);
		});
		
		return new ResponseEntity<String>("Success...", HttpStatus.OK);
	}

}
