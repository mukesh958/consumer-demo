package com.mak.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/service")
public class TestController {
	
	@GetMapping("/getdata")
	public String getData() {
		return "Hi Mukesh! How are you.";
	}

}
