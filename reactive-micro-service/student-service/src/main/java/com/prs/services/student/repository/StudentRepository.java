package com.prs.services.student.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.prs.services.student.entity.StudentEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentRepository extends ReactiveCrudRepository<StudentEntity, Long> {
	Mono<StudentEntity> findById(Long id);

	Flux<StudentEntity> findByCollegeId(Long collegeId);

	Flux<StudentEntity> findByDepartmentId(Long departmentId);
}
