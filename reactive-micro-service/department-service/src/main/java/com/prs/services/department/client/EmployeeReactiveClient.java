package com.prs.services.department.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.prs.services.department.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeReactiveClient {

	@Autowired
	private ExternalClient externalClient;

	public Flux<Employee> findByDepartment(Long departmentId) {
		return externalClient.getForFlux("/department/" + departmentId, getDefaultHeaders(), Employee.class);
	}

	public Mono<Employee> addEmployee(Employee employee) {
		return externalClient.postForMono("/", getDefaultHeaders(), Employee.class, employee, Employee.class);

	}

	private HttpHeaders getDefaultHeaders() {
		HttpHeaders h = new HttpHeaders();
		h.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return h;
	}

}
