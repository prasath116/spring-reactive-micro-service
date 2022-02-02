
package com.prs.services.employee.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Table("Employee")
@ToString
public class EmployeeEntity {

	@Id
	private Long id;
	private String name;
	private int age;
	private String position;

	@Column("college_id")
	private Long collegeId;
	@Column("department_id")
	private Long departmentId;

}
