package com.prs.services.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.prs.services.aop.AspectLog.AspectLogBuilder;

import reactor.core.publisher.Mono;


@Aspect
@Component
public class ExternalLoggingAspect extends AbstractLogger {
    final Logger LOGGER = LogManager.getLogger(ExternalLoggingAspect.class);

    @Pointcut("execution(public * *(..))") //this should work for the public pointcut
    private void anyPublicOperation() {}

    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)") //this should work for the annotation service pointcut
    private void inFeignClient() {}

    @Around("anyPublicOperation() && inFeignClient()")
    public Object externalLogFeign(ProceedingJoinPoint joinPoint) throws Throwable {
    	final AspectLogBuilder builder = AspectLog.builder();
    	Mono<Object> respMono = null;
    	try {
    		long start = System.currentTimeMillis();
    		Object res = joinPoint.proceed();
    		long end = System.currentTimeMillis() - start;
    		builder.timeTaken(end);
    		
    		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	    Method method = signature.getMethod();
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
//    		builder.path(request.getServletPath());
//    		builder.headers(getHttpHeaders(request));
    		
    		respMono = getResponseMono(res);
    		return res;
		} catch (Exception e) {
			e.printStackTrace();
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

	@Around(value = "execution(public * com.prs.services.*.client.ExternalClient.*(..))")  
    public Object externalLog(ProceedingJoinPoint joinPoint) throws Throwable {
    	final AspectLogBuilder builder = AspectLog.builder();
    	Mono<Object> respMono = null;
    	try {
    		long start = System.currentTimeMillis();
    		Object res = joinPoint.proceed();
    		long end = System.currentTimeMillis() - start;
    		List<Object> argList = Arrays.asList(Optional.ofNullable(joinPoint.getArgs()).orElse(new String[] {""}));
    		
			builder.timeTaken(end)
					.path(argList.isEmpty() ? "" : (String) argList.get(0))
					.headers(argList.size() < 2 ? null : (HttpHeaders) argList.get(1))
					.requestBody(argList.size() > 3 ? argList.get(3):null);
			
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
