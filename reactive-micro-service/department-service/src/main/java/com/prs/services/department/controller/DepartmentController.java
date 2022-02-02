package com.prs.services.department.controller;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.services.department.client.EmployeeReactiveClient;
import com.prs.services.department.entity.DepartmentEntity;
import com.prs.services.department.model.Department;
import com.prs.services.department.model.Employee;
import com.prs.services.department.service.IDepartmentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RefreshScope
public class DepartmentController {

	private static final Logger LOGGER = LogManager.getLogger(DepartmentController.class);
	
	@Value("${environment.decription}")
	private String environmentDescription;
	
	@Autowired
	private IDepartmentService service;
	
	@Autowired
	private EmployeeReactiveClient employeeReactiveClient;
	
	@PostMapping("/add")
	public Mono<Department> add(@RequestBody DepartmentEntity department) {
		LOGGER.info("Department add: {}", department);
		return service.save(department);
	}
	
	@GetMapping("/get-by/{id}")
	public Mono<Department> findById(@PathVariable("id") Long id) {
		LOGGER.info("Department find: id={}", id);
		return service.findById(id);
	}
	
	@GetMapping("/findAll")
	public Flux<Department> findAll() {
		LOGGER.debug(environmentDescription+"Env .");
		return service.findAll();
	}
	
	@GetMapping("/college/{collegeId}")
	public Flux<Department> findByCollege(@PathVariable("collegeId") Long collegeId) {
		LOGGER.info("Department find: collegeId={}", collegeId);
		return service.findByCollege(collegeId);
	}
	
	@GetMapping("/college/{collegeId}/with-employees")
	public Flux<Department> findByCollegeWithEmployees(@PathVariable("collegeId") Long collegeId) {
		LOGGER.info("Department find: collegeId={}", collegeId);
		Flux<Department> departments = service.findByCollege(collegeId);
		return departments.flatMap(d -> {
			return employeeReactiveClient.findByDepartment(d.getId()).collectList().map(l-> {
				d.setEmployees(l);
				return d;
			});
		});
	}
	
	@PostMapping("/addEmployee")
	public Mono<Employee> addEmployee(@RequestBody Employee employee) {
		return employeeReactiveClient.addEmployee(employee);
	}
	
	@PostConstruct
	private void initiated() {
		LOGGER.debug(environmentDescription+" DepartmentController initiated.");
	}
	
}
