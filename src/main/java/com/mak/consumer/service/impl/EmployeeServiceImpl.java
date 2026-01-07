package com.mak.consumer.service.impl;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mak.consumer.client.EmployeeClient;
import com.mak.consumer.model.EmpRequset;
import com.mak.consumer.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Value("${springbootURL.baseURL}")
	private String baseURL;
	
	@Value("${springbootURL.saveEmployee}")
	private String saveEmployee;
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	EmployeeClient employeeClient;
	
	
	@Override
	public String saveEmployee(EmpRequset emp) {
		String url=baseURL+saveEmployee;
		 System.out.println("saveEmployee method called "+new Date());
		return restTemplate.postForObject(url, emp, String.class);
	}
	
	
	@Override
	public Integer getAllAvalEmpList() {
		System.out.println("Service called");
		int empcount=0;
		int errorCount=0;
		long start=System.currentTimeMillis();
		for(int i=0;i<1;i++) {
			System.out.println("i= "+i);
			employeeClient.employeeCounts();
			//CompletableFuture<Boolean> res=employeeClient.employeeCount();
			try {
				//Boolean boolean1 = res.get();
				//System.out.println("=== "+res.get());
				empcount++;
			} catch (Exception e) {
				errorCount++;
				e.printStackTrace();
			}
		}
		long end=System.currentTimeMillis();
		System.out.println("Total Time :: "+(end-start));
		System.out.println("empcount "+empcount +" errorCount "+errorCount);
		return empcount;
	}
}
