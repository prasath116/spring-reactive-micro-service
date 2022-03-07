package com.prs.services.student.service;

import com.prs.services.student.entity.StudentEntity;
import com.prs.services.student.model.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStudentService {

	Mono<Student> save(StudentEntity entity);
	
	Mono<Student> update(Long id, StudentEntity entity);

	Flux<Student> findAll();

	Mono<Student> findById(Long id);

	Flux<Student> findByCollege(Long collegeId);

	Flux<Student> findByDepartment(Long departmentId);

	Mono<Void> deleteById(Long id);

}
