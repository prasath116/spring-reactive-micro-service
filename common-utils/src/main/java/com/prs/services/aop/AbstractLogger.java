package com.prs.services.aop;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractLogger {

	@Autowired
	ObjectMapper mapper;

	abstract Logger getLogger();

	public Mono<Object> getResponseMono(Object res) {
		Mono<Object> respMono;
		if (res instanceof Flux) {
			respMono = ((Flux<?>) res).collectList().map(o -> {
				return o;// return getJsonString(o);
			});
		} else if (res instanceof Mono) {
			respMono = ((Mono<?>) res).map(o -> {
				return o;// return getJsonString(o);
			});
		} else {
			respMono = Mono.just(res);
		}
		return respMono;
	}

	public String getJsonString(Object o) {
		try {
			return mapper.writeValueAsString(o);
		} catch (Exception e) {
			getLogger().error("Exception in getJsonString : " + e.getMessage());
			return e.getMessage();
		}
	}

	/*
	 * public HttpHeaders getHttpHeaders(HttpServletRequest e) { HttpHeaders headers
	 * = new HttpHeaders(); Collections.list(e.getHeaderNames()).forEach(name -> {
	 * //If needed we can filter only required headers headers.put(name,
	 * Collections.list(e.getHeaders(name))); }); return headers; }
	 */
	public void log(String s) {
		getLogger().info(s);
	}

}
