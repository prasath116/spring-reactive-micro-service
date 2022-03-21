package com.prs.services.student.client;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Component
@AllArgsConstructor
public class ExternalClientImpl implements ExternalClient {
	
	private static final Logger LOGGER = LogManager.getLogger(ExternalClientImpl.class);
	
	private WebClient client;
	
	public <T> Mono<T> getForMono(String path, HttpHeaders httpHeaders, Class<T> resClazz) {
		return getClientResponse(path, httpHeaders)
				.exchangeToMono(r -> r.bodyToMono(resClazz));
	}

	public <T> Flux<T> getForFlux(String path, HttpHeaders httpHeaders, Class<T> resClazz) {
		return getClientResponse(path, httpHeaders)
				.exchangeToFlux(r -> r.bodyToFlux(resClazz));

	}

	public <T, U> Mono<U> postForMono(String path, HttpHeaders httpHeaders, Class<U> resClazz,
			T reqBody, Class<T> reqClazz) {
		return postForResponse(path, httpHeaders, reqBody, reqClazz)
				.exchangeToMono(r -> r.bodyToMono(resClazz));
	}

	public <T, U> Flux<U> postForFlux(String path, HttpHeaders httpHeaders, T reqBody, Class<T> reqClazz,
			Class<U> resClazz) {
		return postForResponse(path, httpHeaders, reqBody, reqClazz)
				.exchangeToFlux(r -> r.bodyToFlux(resClazz));

	}
	
	private <T> RequestHeadersSpec<?> postForResponse(String path, HttpHeaders httpHeaders, T reqBody,
			Class<T> reqClazz) {
		return client.post()
				.uri(path)
				.headers(headerConsumer(httpHeaders))
				.body(Mono.just(reqBody), reqClazz);
	}
	
	private RequestHeadersSpec<?> getClientResponse(String path, HttpHeaders httpHeaders) {
		return client.get()
				.uri(path)
				.headers(headerConsumer(httpHeaders));
	}
	
	@PostConstruct
	private void initiated() {
		LOGGER.debug("ExternalClientImpl initiated");
	}

}
