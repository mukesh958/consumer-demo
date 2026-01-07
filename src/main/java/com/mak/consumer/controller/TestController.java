package com.mak.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mak.consumer.config.AppProperties;

@RestController
@RequestMapping("/v1/service")
public class TestController {
	
	@Autowired
	private AppProperties appProperties;
	
	@GetMapping("/getdata")
	public String getData() {
		System.out.println(appProperties);
		return "Hi Mukesh! How are you.";
	}

}
