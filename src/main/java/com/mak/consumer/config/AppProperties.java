package com.mak.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	String name;
	String version;
	String description;
	String license;
	String licenseUrl;
	
}
