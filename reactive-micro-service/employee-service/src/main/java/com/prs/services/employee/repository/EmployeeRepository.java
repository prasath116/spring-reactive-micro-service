
package com.prs.services.employee.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.prs.services.employee.entity.EmployeeEntity;

import reactor.core.publisher.Flux;

public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeEntity, Long> {

	Flux<EmployeeEntity> findByCollegeId(Long collegeId);

	Flux<EmployeeEntity> findByDepartmentId(Long departmentId);
}
