
package com.prs.services.employee.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Table("Employee")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity/* implements Persistable<Long> */{

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private int age;
	private String position;

	@Column("college_id")
	private Long collegeId;
	@Column("department_id")
	private Long departmentId;
	
	/*
	 * @Transient private boolean newEmployee;
	 * 
	 * @Override
	 * 
	 * @Transient public boolean isNew() { return this.newEmployee || id == null; }
	 * 
	 * public EmployeeEntity setAsNew() { this.newEmployee = true; return this; }
	 */

}
