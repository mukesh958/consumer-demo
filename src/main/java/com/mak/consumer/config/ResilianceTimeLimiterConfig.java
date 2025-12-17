package com.mak.consumer.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

@Configuration
public class ResilianceTimeLimiterConfig {
	
	private static final String resilienceTimeLimitorTimeout="resilience4j.timelimiter.instances.%s.timeoutDuration";
	private static final String resilienceTimeLimitorCanceledRunning="resilience4j.timelimiter.instances.%s.cancelRunningFuture";
	private static final String resilienceThreadPool="resilience4j.thread-pool-bulkhead.instances.%s.coreThreadPoolSize";
	
	
	
	@Value("${resilience4j.backends}")
	List<String> backends;
	
	private Map<String, TimeLimiter> timeLimiterConfigurations;
	
	
	public ResilianceTimeLimiterConfig() {
		this.timeLimiterConfigurations = new HashMap<>();
	}

	@Bean(name = "timeLimiterRegistry")
	public TimeLimiterRegistry timeLimiterRegistry(Environment env) {
		TimeLimiterRegistry timeLimiterRegistry = TimeLimiterRegistry.ofDefaults();
		backends.forEach(backend->{
			TimeLimiterConfig config= TimeLimiterConfig.custom()
					.cancelRunningFuture(Boolean.parseBoolean(
							env.getProperty(resilienceTimeLimitorCanceledRunning.formatted(backend))))
					.timeoutDuration(Duration.ofSeconds(
							Long.parseLong(env.getProperty(resilienceTimeLimitorTimeout.formatted(backend)))))
					.build();
			TimeLimiter timeLimiter=TimeLimiter.of(backend, config);
			timeLimiterConfigurations.put(backend, timeLimiter);
			timeLimiterRegistry.addConfiguration(backend,config);
		});
		return timeLimiterRegistry;
	}

	public TimeLimiter getTimeLimiter(String backend) {
		return timeLimiterConfigurations.get(backend);
	}
	public ScheduledExecutorService getScheduledExecutorService(String configName, Environment env) {
		//return java.util.concurrent.Executors.newScheduledThreadPool(5);
		return new ScheduledThreadPoolExecutor(Integer.parseInt(env.getProperty(resilienceThreadPool.formatted(configName))));
	}
}
