package com.prs.services.employee.service;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.services.employee.entity.EmployeeEntity;
import com.prs.services.employee.model.Employee;
import com.prs.services.employee.repository.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Override
	public Mono<Employee> save(EmployeeEntity employee) {
		Mono<EmployeeEntity> c = repository.save(employee);
		return c.map(mapper);
	}

	@Override
	public Flux<Employee> findAll() {
		return repository.findAll().map(mapper);
	}

	@Override
	public Mono<Employee> findById(Long id) {
		return repository.findById(id).map(mapper);
	}

	@Override
	public Flux<Employee> findByCollege(Long collegeId) {
		return repository.findByCollegeId(collegeId).map(mapper);
	}

	@Override
	public Flux<Employee> findByDepartment(Long departmentId) {
		return findAll().filter(a -> a.getDepartmentId().equals(departmentId));
//		return repository.findByDepartmentId(departmentId).map(mapper);
	}

	private Function<EmployeeEntity, Employee> mapper = c -> {
		Employee response = new Employee();
		BeanUtils.copyProperties(c, response);
		return response;
	};

}
