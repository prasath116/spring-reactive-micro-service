package com.prs.services.department.client.clientRequestLog;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;

class SingleWriterContext implements BodyInserter.Context {

	private final List<HttpMessageWriter<?>> singleWriterList;

	SingleWriterContext(HttpMessageWriter<?> writer) {
		this.singleWriterList = Arrays.asList(writer);
	}

	@Override
	public List<HttpMessageWriter<?>> messageWriters() {
		return singleWriterList;
	}

	@Override
	public Optional<ServerHttpRequest> serverRequest() {
		return Optional.empty();
	}

	@Override
	public Map<String, Object> hints() {
		return null;
	}
}
