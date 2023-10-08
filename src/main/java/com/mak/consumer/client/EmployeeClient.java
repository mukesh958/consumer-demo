package com.mak.consumer.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mak.consumer.model.Emp;


@Service

public class EmployeeClient {
	@Autowired
	RestTemplate restTemplate;
	@Async
	public CompletableFuture<Boolean> employeeCount() {
		System.out.println("=== "+Thread.currentThread().getName());
		HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.add("name", "Mukesh Gupta");
		List<Emp> res=
				(List<Emp> )restTemplate.getForObject("http://localhost:8080/springootbasic/v1/rest/employee/allEmployee",
				Object.class);
		System.out.println(" res= "+res);
		return CompletableFuture.completedFuture(Boolean.TRUE);
	}
}
