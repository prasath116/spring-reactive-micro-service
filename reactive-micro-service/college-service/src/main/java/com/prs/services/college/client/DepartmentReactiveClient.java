package com.prs.services.college.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.prs.services.college.model.Department;

import reactor.core.publisher.Flux;

@Component
public class DepartmentReactiveClient {

	@Autowired
	@Qualifier("departmentExternalClient")
	private ExternalClient externalClient;
	
	public Flux<Department> findByCollege(Long collegeId) {
		return externalClient.getForFlux("/college/" + collegeId, getDefaultHeaders(), Department.class);
	}
	
	public Flux<Department> findByCollegeWithEmployees(Long collegeId) {
		return externalClient.getForFlux("/college/" + collegeId+"/with-employees", getDefaultHeaders(), Department.class);
	}

	private HttpHeaders getDefaultHeaders() {
		HttpHeaders h = new HttpHeaders();
		h.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return h;
	}

	
}
