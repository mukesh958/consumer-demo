package com.mak.consumer.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.mak.consumer.model.Emp;


@Service

public class EmployeeClient {
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${springboot.allEmp.url}")
	private String mainServiceUrl;
	
	@Async
	public CompletableFuture<Boolean> employeeCount() {
		System.out.println("=== "+Thread.currentThread().getName());
		HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.add("name", "Mukesh Gupta");
		try {
			Emp[] resArr = restTemplate.getForObject(mainServiceUrl,
					Emp[].class);
			List<Emp> res = resArr == null ? Collections.emptyList() : Arrays.asList(resArr);
			System.out.println(" res= " + res);
		} catch (RestClientException ex) {
			System.err.println("Failed to call MAINSERVICE: " + ex.getMessage());
			return CompletableFuture.completedFuture(Boolean.FALSE);
		}
		return CompletableFuture.completedFuture(Boolean.TRUE);
	}
	public void employeeCounts() {
		HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.add("name", "Mukesh Gupta");
		System.out.println(mainServiceUrl);
		try {
			Emp[] resArr = restTemplate.getForObject(mainServiceUrl,
					Emp[].class);
			List<Emp> res = resArr == null ? Collections.emptyList() : Arrays.asList(resArr);
			System.out.println(" res= " + res);
		} catch (RestClientException ex) {
			ex.printStackTrace();
		}
	}
}