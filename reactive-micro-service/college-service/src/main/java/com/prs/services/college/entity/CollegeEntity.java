package com.prs.services.college.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("College")
public class CollegeEntity {

	@Id
	private Long id;
	private String name;
	private String address;

	@Override
	public String toString() {
		return "College [id=" + id + ", name=" + name + ", address=" + address + "]";
	}

}
