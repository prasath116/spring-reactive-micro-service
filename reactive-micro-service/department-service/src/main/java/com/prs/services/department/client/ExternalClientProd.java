package com.prs.services.department.client;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
@Profile({"prod"})
public class ExternalClientProd implements ExternalClient {
	
	private static final Logger LOGGER = LogManager.getLogger(ExternalClientProd.class);
	
	@Autowired
	@Qualifier("employeeWebClient")
	private WebClient client;

	public <T> Mono<T> getForMono(String path, HttpHeaders httpHeaders, Class<T> resClazz) {
		return getClientResponse(path, httpHeaders).bodyToMono(resClazz);
	}

	public <T> Flux<T> getForFlux(String path, HttpHeaders httpHeaders, Class<T> resClazz) {
		return getClientResponse(path, httpHeaders).bodyToFlux(resClazz);

	}

	public <T, U> Mono<U> postForMono(String path, HttpHeaders httpHeaders, Class<U> resClazz,
			T reqBody, Class<T> reqClazz) {
		return postForResponse(path, httpHeaders, reqBody, reqClazz).bodyToMono(resClazz);
	}

	public <T, U> Flux<U> postForFlux(String path, HttpHeaders httpHeaders, T reqBody, Class<T> reqClazz,
			Class<U> resClazz) {
		return postForResponse(path, httpHeaders, reqBody, reqClazz).bodyToFlux(resClazz);
	}
	
	private <T> ResponseSpec postForResponse(String path, HttpHeaders httpHeaders, T reqBody,
			Class<T> reqClazz) {
		return client.post()
				.uri(path)
				.headers(headerConsumer(httpHeaders))
				.body(Mono.just(reqBody), reqClazz)
				.retrieve();
	}
	
	private ResponseSpec getClientResponse(String path, HttpHeaders httpHeaders) {
		return client.get()
				.uri(path)
				.headers(headerConsumer(httpHeaders))
				.retrieve();
	}
	
	@PostConstruct
	private void initiated() {
		LOGGER.debug("ExternalClientProd initiated");
	}
	
}
