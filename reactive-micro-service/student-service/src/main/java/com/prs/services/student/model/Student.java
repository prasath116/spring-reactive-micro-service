package com.prs.services.student.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

	private Long id;
	private Long collegeId;
	private Long departmentId;
	private String name;
	private int age;
	private String position;

	@Override
	public String toString() {
		return "Employee [id=" + id + ", collegeId=" + collegeId + ", departmentId=" + departmentId
				+ ", name=" + name + ", position=" + position + "]";
	}

}
