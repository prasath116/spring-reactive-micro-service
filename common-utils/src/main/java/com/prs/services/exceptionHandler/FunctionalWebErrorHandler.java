package com.prs.services.exceptionHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class FunctionalWebErrorHandler implements ErrorWebExceptionHandler {
	
	private static final Logger log = LogManager.getLogger(GlobalErrorHandler.class);
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("Exception Message is : {} ", ex.getMessage(), ex);

        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        var errorMessage = bufferFactory.wrap(ex.getMessage().getBytes());
        if(ex instanceof NotFoundException){
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return exchange.getResponse().writeWith(Mono.just(errorMessage));
        }

        if(ex instanceof DataException){
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().writeWith(Mono.just(errorMessage));
        }
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return exchange.getResponse().writeWith(Mono.just(errorMessage));
    }
}
