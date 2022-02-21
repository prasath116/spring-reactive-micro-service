package com.prs.services.employee.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.services.employee.model.Employee;
import com.prs.services.employee.service.IEmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {

	private static final Logger LOGGER = LogManager.getLogger(EmployeeController.class);
	
	@Autowired
	IEmployeeService service;
	
	@PostMapping("/add")
	public Mono<Employee> add(@RequestBody @Valid Employee employee) {
		LOGGER.info("Employee add: {}", employee);
		return service.save(employee);
	}
	
	@PutMapping("/update/{id}")
	public Mono<Employee> update(@PathVariable("id") Long id, @RequestBody @Valid Employee employee) {
		LOGGER.info("Employee update: {}", employee);
		return service.update(id,employee);
	}
	
	/*@GetMapping("/get-by/{id}")
	public Mono<Employee> findById(@PathVariable("id") Long id) {
		LOGGER.info("Employee find: id={}", id);
		return service.findById(id);
	}
	
	@GetMapping("/findAll")
	public Flux<Employee> findAll() {
		LOGGER.info("Employee find");
		return service.findAll();
	}*/
	
	@GetMapping("/department/{departmentId}")
	public Flux<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
		LOGGER.info("Employee find: departmentId={}", departmentId);
		return service.findByDepartment(departmentId);
	}
	
	@GetMapping("/college/{collegeId}")
	public Flux<Employee> findByCollege(@PathVariable("collegeId") Long collegeId) {
		LOGGER.info("Employee find: collegeId={}", collegeId);
		return service.findByCollege(collegeId);
	}
	
}
