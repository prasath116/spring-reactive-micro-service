package com.prs.services.student.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.services.student.entity.StudentEntity;
import com.prs.services.student.model.Student;
import com.prs.services.student.service.IStudentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	IStudentService service;
	
	@PostMapping("/add")
	public Mono<Student> add(@RequestBody StudentEntity employee) {
		LOGGER.info("Student add: {}", employee);
		return service.save(employee);
	}
	
	@GetMapping("/get-by/{id}")
	public Mono<Student> findById(@PathVariable("id") Long id) {
		LOGGER.info("Student find: id={}", id);
		return service.findById(id);
	}
	
	@GetMapping("/findAll")
	public Flux<Student> findAll() {
		LOGGER.info("Student find");
		return service.findAll();
	}
	
	@GetMapping("/department/{departmentId}")
	public Flux<Student> findByDepartment(@PathVariable("departmentId") Long departmentId) {
		LOGGER.info("Student find: departmentId={}", departmentId);
		return service.findByDepartment(departmentId);
	}
	
	@GetMapping("/college/{collegeId}")
	public Flux<Student> findByCollege(@PathVariable("collegeId") Long collegeId) {
		LOGGER.info("Student find: collegeId={}", collegeId);
		return service.findByCollege(collegeId);
	}
	
}
