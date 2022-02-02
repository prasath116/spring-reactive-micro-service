package com.prs.services.department.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.prs.services.department.entity.DepartmentEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentRepository extends ReactiveCrudRepository<DepartmentEntity, Long> {
	Mono<DepartmentEntity> findById(Long id);
	Flux<DepartmentEntity> findByCollegeId(Long collegeId);
}
