package com.prs.services.student.client;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExternalClient {
	public <T> Mono<T> getForMono(String path, HttpHeaders httpHeaders, Class<T> resClazz);

	public <T> Flux<T> getForFlux(String path, HttpHeaders httpHeaders, Class<T> resClazz);

	public <T, U> Mono<U> postForMono(String path, HttpHeaders httpHeaders, Class<U> resClazz, T reqBody,
			Class<T> reqClazz);

	public <T, U> Flux<U> postForFlux(String path, HttpHeaders httpHeaders, T reqBody, Class<T> reqClazz,
			Class<U> resClazz);
	
	default Consumer<HttpHeaders> headerConsumer(HttpHeaders httpHeaders) {
		return h -> h.putAll(httpHeaders);
	}
	
	default Consumer<HttpHeaders> headerConsumer(Map<String, String> httpHeaders) {
		return h -> httpHeaders.forEach((k, v) -> h.add(k, v));
	}
}
