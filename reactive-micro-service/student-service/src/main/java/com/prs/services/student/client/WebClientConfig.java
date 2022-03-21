package com.prs.services.student.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	@Qualifier("collegeWebClient")
	@LoadBalanced
	public WebClient getCollegeWebClient() {
		WebClient client = WebClient.builder().baseUrl("http://localhost:8060/college")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
		return client;
	}
	
	@Bean
	@Qualifier("departmentWebClient")
	@LoadBalanced
	public WebClient getDepartmentWebClient() {
		WebClient client = WebClient.builder().baseUrl("http://localhost:8060/department")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
		return client;
	}
	
	@Bean
	@Qualifier("collegeExternalClient")
	public ExternalClientImpl getCollegeExternalClient(@Qualifier("collegeWebClient") WebClient collegeWebClient) {
		return new ExternalClientImpl(collegeWebClient);
	}
	
	@Bean
	@Qualifier("departmentExternalClient")
	public ExternalClientImpl getDepartmentExternalClient(@Qualifier("departmentWebClient") WebClient departmentWebClient) {
		return new ExternalClientImpl(departmentWebClient);
	}


}
