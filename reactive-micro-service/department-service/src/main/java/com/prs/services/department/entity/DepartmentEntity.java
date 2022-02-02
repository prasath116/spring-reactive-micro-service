package com.prs.services.department.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Table("Department")
@ToString
public class DepartmentEntity {

	@Id
	private Long id;
	private String name;
	
	@Column("college_id")
	private Long collegeId;
}
