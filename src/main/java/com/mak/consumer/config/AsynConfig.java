package com.mak.consumer.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsynConfig {
	@Bean
	  public Executor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(10);
	    executor.setMaxPoolSize(200);
	    executor.setQueueCapacity(5000);
	    executor.setThreadNamePrefix("GithubLookup-");
	    executor.initialize();
	    return executor;
	  }
}
