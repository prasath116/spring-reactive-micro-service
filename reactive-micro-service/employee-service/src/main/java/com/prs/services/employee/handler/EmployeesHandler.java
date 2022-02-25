package com.prs.services.employee.handler;

import java.util.Optional;

import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.prs.services.employee.model.Employee;
import com.prs.services.employee.service.IEmployeeService;
import com.prs.services.exceptionHandler.NotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeesHandler {
	private static final Logger log = LogManager.getLogger(EmployeesHandler.class);
	
	@Autowired
	IEmployeeService service;

    @Autowired
    private Validator validator;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    static Mono<ServerResponse> badRequest = ServerResponse.badRequest().build();
    
    private Mono<ServerResponse> buildResponse(Flux<Employee> employees) {
        return ServerResponse.ok()
                .body(employees, Employee.class);
    }
    
    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        var employees = service.findAll();
        return buildResponse(employees);
    }
    
	public Mono<ServerResponse> findById(ServerRequest serverRequest) {
		var id = Long.valueOf(serverRequest.pathVariable("id"));
		var employee = service.findById(id);
		return employee.flatMap(e-> {
			if(e== null) {
				log.error("employee is null");
			}
			return ServerResponse.ok().body(Mono.just(e), Employee.class);
		}).switchIfEmpty(Mono.error(new NotFoundException("Employee not available for employee id : "+id)));
	}

}
