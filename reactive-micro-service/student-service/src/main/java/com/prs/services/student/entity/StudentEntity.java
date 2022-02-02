package com.prs.services.student.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Table("Student")
@ToString
public class StudentEntity {

	@Id
	private Long id;
	private String name;
	private int age;

	@Column("college_id")
	private Long collegeId;
	@Column("department_id")
	private Long departmentId;
}
