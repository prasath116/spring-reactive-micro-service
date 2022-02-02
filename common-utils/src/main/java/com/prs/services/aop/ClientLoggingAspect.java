package com.prs.services.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.prs.services.aop.AspectLog.AspectLogBuilder;

import reactor.core.publisher.Mono;


@Aspect
@Component
public class ClientLoggingAspect extends AbstractLogger {
	final Logger LOGGER = LogManager.getLogger(ClientLoggingAspect.class);
    
    @Around(value = "execution(* com.prs.services.*.controller.*.*(..))")  
    public Object logControllerAccess(ProceedingJoinPoint joinPoint) throws Throwable {
    	final AspectLogBuilder builder = AspectLog.builder();
    	Mono<Object> respMono = null;
    	try {
    		long start = System.currentTimeMillis();
    		Object res = joinPoint.proceed();
    		long end = System.currentTimeMillis() - start;
    		builder.timeTaken(end);
    		
    		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	    Method method = signature.getMethod();
    	    /*Annotation[] myAnnotation = method.getAnnotations();
    	    Arrays.asList(myAnnotation).forEach(annotation-> {
    	    	if(annotation instanceof RequestMapping) {
    	    		RequestMapping m = (RequestMapping) annotation;
    	    		builder.paths(Arrays.asList(m.value()));
    	    	} else if(annotation instanceof GetMapping) {
    	    		GetMapping m = (GetMapping) annotation;
    	    		builder.paths(Arrays.asList(m.value()));
    	    	} else if(annotation instanceof PostMapping) {
    	    		PostMapping m = (PostMapping) annotation;
    	    		builder.paths(Arrays.asList(m.value()));
    	    	}
    	    });*/
    	    Object[] args = joinPoint.getArgs();
    	    Parameter[] params = method.getParameters();
    	    if(args != null && params != null && args.length == params.length) {
    	    	IntStream.range(0, params.length).forEach(i-> {
    	    		Parameter param = params[i];
    	    		Arrays.asList(param.getAnnotations()).forEach(paramAnnotation-> {
        	    		if(paramAnnotation instanceof PathVariable) {
        	    			PathVariable m = (PathVariable) paramAnnotation;
            	    		builder.pathVariable(m.value(), args[i]);
            	    	} else if(paramAnnotation instanceof RequestBody) {
            	    		builder.requestBody(args[i]);
            	    	}
        	    	});
    	    	});
    	    }
//    	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//    	    builder.path(request.getServletPath());
//    	    builder.headers(getHttpHeaders(request));
    	    
    		respMono = getResponseMono(res);
    		return res;
		} catch (Exception e) {
			
		} finally {
			if(respMono != null) {
				respMono.subscribe(s -> {
					builder.responseBody(s);
					log(getJsonString(builder.build())); //Json format
					log(builder.build().toString()); //log format
				});
			}
			
		}
		return null;
    }

	@Override
	Logger getLogger() {
		return LOGGER;
	}
    
}
