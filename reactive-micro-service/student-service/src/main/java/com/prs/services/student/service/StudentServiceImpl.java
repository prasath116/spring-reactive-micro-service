package com.prs.services.student.service;

import java.util.Arrays;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.services.exceptionHandler.DataException;
import com.prs.services.student.client.CollegeReactiveClient;
import com.prs.services.student.client.DepartmentReactiveClient;
import com.prs.services.student.entity.StudentEntity;
import com.prs.services.student.model.College;
import com.prs.services.student.model.Department;
import com.prs.services.student.model.Student;
import com.prs.services.student.repository.StudentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentRepository repository;
	
	@Autowired
	private DepartmentReactiveClient departmentClient;
	@Autowired
	private CollegeReactiveClient collegeClient;
	

	@Override
	public Mono<Student> save(StudentEntity entity) {
//		return repository.save(entity).map(mapper); Issue in SimpleR2dbcRepository save(e); So used saveAll for time being
		return repository.saveAll(Arrays.asList(entity)).last().map(simpleMapper);
	}

	@Override
	public Mono<Student> update(Long id, StudentEntity entity) {
		return findById(id).flatMap(s-> {
			entity.setId(id);
			return save(entity);
		}).switchIfEmpty(Mono.error(new DataException("Student not fount for id : "+id)));
	}
	
	@Override
	public Flux<Student> findAll() {
		return repository.findAll().map(simpleMapper);
	}

	@Override
	public Mono<Student> findById(Long id) {
		return repository.findById(id).flatMap(deepMapper);
	}

	@Override
	public Mono<Void> deleteById(Long id) {
		return repository.deleteById(id);
	}
	
	@Override
	public Flux<Student> findByCollege(Long collegeId) {
		return findAll().filter(a -> a.getCollege().getId().equals(collegeId));
	}

	@Override
	public Flux<Student> findByDepartment(Long departmentId) {
		return findAll().filter(a -> a.getDepartment().getId().equals(departmentId));
	}

	private Function<StudentEntity, Student> simpleMapper = c -> {
		var student = new Student();
		BeanUtils.copyProperties(c, student);
		return student;
	};
	
	private Function<StudentEntity, Mono<Student>> deepMapper = c -> {
		var student = simpleMapper.apply(c);
		var college = collegeClient.findByCollege(c.getCollegeId());
		var department = departmentClient.findByDepartment(c.getDepartmentId());
		var tuple = Mono.zip(college,department);
		return tuple.map(t-> {
			student.setCollege(new College());
			student.setDepartment(new Department());
			BeanUtils.copyProperties(t.getT1(), student.getCollege());
			BeanUtils.copyProperties(t.getT2(), student.getDepartment());
			return student;
		});
	};
	
}
