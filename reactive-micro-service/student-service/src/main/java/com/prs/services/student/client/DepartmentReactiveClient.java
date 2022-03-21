package com.prs.services.student.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.prs.services.student.model.Department;

import reactor.core.publisher.Mono;

@Component
public class DepartmentReactiveClient {

	@Autowired
	@Qualifier("departmentExternalClient")
	private ExternalClient externalClient;
	
	public Mono<Department> findByDepartment(Long departmentId) {
		return externalClient.getForMono("/get-by/" + departmentId, getDefaultHeaders(), Department.class);
	}

	private HttpHeaders getDefaultHeaders() {
		HttpHeaders h = new HttpHeaders();
		h.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return h;
	}

	
}
