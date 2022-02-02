package com.prs.services.department.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prs.services.department.client.clientRequestLog.RequestBodyValueReceiver;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

	@Autowired
	private ObjectMapper mapper;
	
	@Bean
	@Qualifier("employeeWebClient")
	@LoadBalanced
	public WebClient getEmployeeWebClient() {
		WebClient client = WebClient.builder().baseUrl("http://localhost:8060/employee")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				.filter(logFilter)
				.build();
		return client;
	}
	
	ExchangeFilterFunction logFilter = (request, next) -> {
		System.out.println("Request body : "+logRequest(request));
		Mono<ClientResponse> clientResponse = next.exchange(request);
		return clientResponse.flatMap(response -> {
			return logResponse(response);
		});
	};
	
	private String logRequest(ClientRequest request) {
		String requestBody = null;
		if(request.method() == HttpMethod.POST) {
			Object o = extractBody(request);
			if(o instanceof String) {
				requestBody = o.toString();
			} else {
				try {
					requestBody = mapper.writeValueAsString(o);
					System.out.println("Request body : "+mapper.writeValueAsString(o));
				} catch (JsonProcessingException e) {
					requestBody = e.getMessage();
				}
			}
		}
		return requestBody;
	}

	private Mono<? extends ClientResponse> logResponse(ClientResponse response) {
		logResponseStatus(response);
		logResponseHeaders(response);
		return logResponseBody(response);
	}

	private static void logResponseStatus(ClientResponse response) {
		HttpStatus status = response.statusCode();
		System.out.println("Returned staus code : " + status.value() + " (" + status.getReasonPhrase() + ")");
	}

	private static Mono<ClientResponse> logResponseBody(ClientResponse response) {
		if (response.statusCode() != null
				&& (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
			System.out.println("Error :"+response.statusCode());
		} else {
			System.out.println("Success :"+response.statusCode());
		}
		return Mono.just(response);
	}
	
	private static void logResponseHeaders(ClientResponse response) {
		response.headers().asHttpHeaders().forEach((name, values) -> {
			values.forEach(value -> {
				System.out.println("name: " + name + " value : " + value);
			});
		});
	}

	static Object extractBody(ClientRequest request) {
		return RequestBodyValueReceiver.getInstance().receiveValue(request.body());
	}
}
