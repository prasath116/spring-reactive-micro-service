package com.prs.services.department.client;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
@Profile({"dev", "native", "default"})
public class ExternalClientDev implements ExternalClient {
	
	private static final Logger LOGGER = LogManager.getLogger(ExternalClientDev.class);
	
	@Autowired
	@Qualifier("employeeWebClient")
	private WebClient client;

	public <T> Mono<T> getForMono(String path, HttpHeaders httpHeaders, Class<T> resClazz) {
		return getClientResponse(path, httpHeaders)
				.flatMap(r -> r.bodyToMono(resClazz));
	}

	public <T> Flux<T> getForFlux(String path, HttpHeaders httpHeaders, Class<T> resClazz) {
		return getClientResponse(path, httpHeaders)
				.flatMapMany(r -> r.bodyToFlux(resClazz));

	}

	public <T, U> Mono<U> postForMono(String path, HttpHeaders httpHeaders, Class<U> resClazz,
			T reqBody, Class<T> reqClazz) {
		return postForResponse(path, httpHeaders, reqBody, reqClazz)
				.flatMap(r -> r.bodyToMono(resClazz));
	}

	public <T, U> Flux<U> postForFlux(String path, HttpHeaders httpHeaders, T reqBody, Class<T> reqClazz,
			Class<U> resClazz) {
		return postForResponse(path, httpHeaders, reqBody, reqClazz)
				.flatMapMany(r -> r.bodyToFlux(resClazz));

	}
	
	private <T> Mono<ClientResponse> postForResponse(String path, HttpHeaders httpHeaders, T reqBody,
			Class<T> reqClazz) {
		return client.post()
				.uri(path)
				.headers(headerConsumer(httpHeaders))
				.body(Mono.just(reqBody), reqClazz)
				.exchange();
	}
	
	private Mono<ClientResponse> getClientResponse(String path, HttpHeaders httpHeaders) {
		return client.get()
				.uri(path)
				.headers(headerConsumer(httpHeaders))
				.exchange();
	}
	
	@PostConstruct
	private void initiated() {
		LOGGER.debug("ExternalClientDev initiated");
	}
	
}
