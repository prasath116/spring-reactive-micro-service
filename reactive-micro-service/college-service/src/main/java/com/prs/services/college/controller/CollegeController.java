package com.prs.services.college.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.services.college.client.DepartmentReactiveClient;
import com.prs.services.college.client.EmployeeReactiveClient;
import com.prs.services.college.entity.CollegeEntity;
import com.prs.services.college.model.College;
import com.prs.services.college.service.ICollegeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CollegeController {

	private static final Logger LOGGER = LogManager.getLogger(CollegeController.class);
	
	@Autowired
	private ICollegeService service;
	@Autowired
	private DepartmentReactiveClient departmentClient;
	@Autowired
	private EmployeeReactiveClient employeeClient;
	
	@PostMapping("/add")
	public Mono<College> add(@RequestBody CollegeEntity college) {
		LOGGER.info("College add: {}", college);
		return service.save(college);
	}
	
	@GetMapping("/findAll")
	public Flux<College> findAll() {
		LOGGER.info("College find");
		return service.findAll();
	}
	
	@GetMapping("/get-by/{id}")
	public Mono<College> findById(@PathVariable("id") Long id) {
		LOGGER.info("College find: id={}", id);
		return service.findById(id);
	}

	@GetMapping("/{id}/with-departments")
	public Mono<College> findByIdWithDepartments(@PathVariable("id") Long id) {
		LOGGER.info("College find: id={}", id);
		return service.findById(id).flatMap(c-> {
			return departmentClient.findByCollege(c.getId()).collectList().map(l->{
				c.setDepartments(l);
				return c;
			});
		});
	}
	
	@GetMapping("/{id}/with-departments-and-employees")
	public Mono<College> findByIdWithDepartmentsAndEmployees(@PathVariable("id") Long id) {
		LOGGER.info("College find: id={}", id);
		return service.findById(id).flatMap(c-> {
			return departmentClient.findByCollegeWithEmployees(c.getId()).collectList().map(l->{
				c.setDepartments(l);
				return c;
			});
		});
	}
	
	@GetMapping("/{id}/with-employees")
	public Mono<College> findByIdWithEmployees(@PathVariable("id") Long id) {
		LOGGER.info("College find: id={}", id);
		return service.findById(id).flatMap(c-> {
			return employeeClient.findByCollege(c.getId()).collectList().map(l->{
				c.setEmployees(l);
				return c;
			});
		});
	}
	
}
