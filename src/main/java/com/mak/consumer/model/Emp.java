package com.mak.consumer.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Emp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String range;
	private Integer salary;
	private LocalDateTime joiningDate;
	

}
