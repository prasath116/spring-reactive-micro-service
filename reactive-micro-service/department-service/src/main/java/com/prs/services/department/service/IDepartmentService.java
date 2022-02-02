package com.prs.services.department.service;

import com.prs.services.department.entity.DepartmentEntity;
import com.prs.services.department.model.Department;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IDepartmentService {

	Mono<Department> save(DepartmentEntity department);

	Flux<Department> findAll();

	Mono<Department> findById(Long id);

	Flux<Department> findByCollege(Long collegeId);

}
