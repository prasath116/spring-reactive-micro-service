package com.prs.services.employee.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

	private Long id;

	@NotNull
	private Long collegeId;
	@NotNull
	private Long departmentId;

	@NotNull(message = "Name should not be null")
	private String name;
	@Min(value = 20, message = "age should be greater than 20.")
	private int age;
	@NotNull(message = "Position should not be null")
	private String position;

	@Override
	public String toString() {
		return "Employee [id=" + id + ", collegeId=" + collegeId + ", departmentId=" + departmentId + ", name=" + name
				+ ", position=" + position + "]";
	}

}
