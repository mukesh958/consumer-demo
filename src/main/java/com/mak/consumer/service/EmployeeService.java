package com.mak.consumer.service;


import org.springframework.stereotype.Service;

import com.mak.consumer.model.EmpRequset;
@Service
public interface EmployeeService{
	public String saveEmployee(EmpRequset emp);
	public Integer getAllAvalEmpList();
}
