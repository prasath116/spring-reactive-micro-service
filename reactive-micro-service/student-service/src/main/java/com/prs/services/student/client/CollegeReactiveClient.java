package com.prs.services.student.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.prs.services.student.model.College;

import reactor.core.publisher.Mono;

@Component
public class CollegeReactiveClient {

	@Autowired
	@Qualifier("collegeExternalClient")
	private ExternalClient externalClient;

	public Mono<College> findByCollege(Long collegeId) {
		return externalClient.getForMono("/get-by/" + collegeId, getDefaultHeaders(), College.class);
	}

	private HttpHeaders getDefaultHeaders() {
		HttpHeaders h = new HttpHeaders();
		h.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return h;
	}

}
