package com.prs.services.employee.service;

import java.util.Arrays;
import java.util.function.BiConsumer;
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
	public Mono<Employee> save(Employee employee) {
//		return repository.save(entity).map(mapper);  Issue in SimpleR2dbcRepository save(e); So used saveAll for time being
		var entity = voToEntityMapper.apply(employee);
		Flux<EmployeeEntity> c = repository.saveAll(Arrays.asList(entity));
		return c.last().map(entityToVomapper);
	}
	
	@Override
	public Mono<Employee> update(Long id, Employee employee) {
		return repository.findById(id)
				.flatMap(entity-> {
					entityToEntityMapper.accept(employee,entity);
					Flux<EmployeeEntity> c = repository.saveAll(Arrays.asList(entity));
					return c.last().map(entityToVomapper);
				});
	}

	@Override
	public Flux<Employee> findAll() {
		return repository.findAll().map(entityToVomapper);
	}

	@Override
	public Mono<Employee> findById(Long id) {
		return repository.findById(id).map(entityToVomapper);
	}

	@Override
	public Flux<Employee> findByCollege(Long collegeId) {
		return repository.findByCollegeId(collegeId).map(entityToVomapper);
	}

	@Override
	public Flux<Employee> findByDepartment(Long departmentId) {
		return findAll().filter(a -> a.getDepartmentId().equals(departmentId));
//		return repository.findByDepartmentId(departmentId).map(mapper);
	}

	private Function<EmployeeEntity, Employee> entityToVomapper = c -> {
		Employee response = new Employee();
		BeanUtils.copyProperties(c, response);
		return response;
	};
	
	private Function<Employee, EmployeeEntity> voToEntityMapper = c -> {
		EmployeeEntity response = new EmployeeEntity();
		BeanUtils.copyProperties(c, response);
		return response;
	};
	
	private BiConsumer<Employee, EmployeeEntity> entityToEntityMapper = (src,dest) -> {
		BeanUtils.copyProperties(src, dest);
	};

}
