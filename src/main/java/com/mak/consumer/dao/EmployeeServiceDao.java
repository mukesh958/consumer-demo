package com.mak.consumer.dao;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mak.consumer.config.ResilianceTimeLimiterConfig;
import com.mak.consumer.model.Emp;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
public class EmployeeServiceDao {
    
    public static final String EMPLOYEE_SERVICE="employeeService";
    private RestTemplate restTemplate;
    private ResilianceTimeLimiterConfig resilianceTimeLimiterConfig;
    Environment env;
    
	
	public EmployeeServiceDao(RestTemplate restTemplate,
			ResilianceTimeLimiterConfig resilianceTimeLimiterConfig) {
		this.restTemplate = restTemplate;
		this.resilianceTimeLimiterConfig = resilianceTimeLimiterConfig;
	}
	 

    @Bulkhead(name = EMPLOYEE_SERVICE, type = Bulkhead.Type.THREADPOOL)
	@CircuitBreaker(name = EMPLOYEE_SERVICE/* , fallbackMethod = "getEmployeeByIdFallback" */)
    public Emp getEmployeeById(Integer id) {
       io.github.resilience4j.timelimiter.TimeLimiter timeLimiter = resilianceTimeLimiterConfig.getTimeLimiter(EMPLOYEE_SERVICE);
    	Supplier<CompletableFuture<Emp>> result= () -> CompletableFuture.supplyAsync(() -> {
			return getEmployeeByIdMain(id);
		});
    	return timeLimiter.executeCompletionStage(resilianceTimeLimiterConfig.getScheduledExecutorService(EMPLOYEE_SERVICE,env), result)
    			.toCompletableFuture().join();
    	
    }

	public Emp getEmployeeByIdMain(Integer id) {
		String url = "http://localhost:8080/springootbasic/v1/rest/employee/" + id;
		ResponseEntity<Emp> responseEntity = restTemplate.getForEntity(url, Emp.class);
		Emp emp = null;
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			emp = responseEntity.getBody();
			System.out.println("Employee Name: " + emp.getName());
		}
		return emp;
	}
    
    // Fallback for TimeLimiter / CircuitBreaker - must return CompletableFuture<Emp>
    public CompletableFuture<Emp> getEmployeeByIdFallback(Integer id, Throwable ex) {
        return CompletableFuture.supplyAsync(() -> {
            Emp emp = new Emp();
            emp.setId(id);
            emp.setName("Fallback Employee");
            return emp;
        });
    }
    
    public void getEmployeeByIdAsync(Integer id) {
    	System.out.println("Call started in Thread: " + Thread.currentThread().getName());
            ResponseEntity<Emp> responseEntity = restTemplate.getForEntity("http://localhost:8080/springootbasic/v1/rest/employee/" + id, Emp.class);
            Emp emp = null;
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                emp = responseEntity.getBody();
                System.out.println("Async Employee Name: " + emp.getName());
            }
            
        System.out.println("Call ended in Thread: " + Thread.currentThread().getName());    
    }
    
    
}