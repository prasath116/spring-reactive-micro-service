package com.prs.services.employee.service;

import com.prs.services.employee.entity.EmployeeEntity;
import com.prs.services.employee.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeService {

	Mono<Employee> save(EmployeeEntity entity);

	Flux<Employee> findAll();

	Mono<Employee> findById(Long id);

	Flux<Employee> findByCollege(Long collegeId);

	Flux<Employee> findByDepartment(Long departmentId);

}
