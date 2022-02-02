package com.prs.services.college.client;

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
	@Qualifier("employeeWebClient")
	@LoadBalanced
	public WebClient getEmployeeWebClient() {
		WebClient client = WebClient.builder().baseUrl("http://localhost:8060/employee")
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
	@Qualifier("employeeExternalClient")
	public ExternalClientImpl getEmployeeExternalClient(@Qualifier("employeeWebClient") WebClient employeeWebClient) {
		return new ExternalClientImpl(employeeWebClient);
	}
	
	@Bean
	@Qualifier("departmentExternalClient")
	public ExternalClientImpl getDepartmentExternalClient(@Qualifier("departmentWebClient") WebClient departmentWebClient) {
		return new ExternalClientImpl(departmentWebClient);
	}


}
