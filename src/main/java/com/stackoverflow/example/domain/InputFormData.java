package com.stackoverflow.example.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
 

public class InputFormData implements Serializable {

	private static final long serialVersionUID = 1L; 
		
	@NotNull
	@NotEmpty
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
