package com.prs.services.employee.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.prs.services.employee.handler.EmployeesHandler;
import com.prs.services.employee.model.Employee;
import com.prs.services.employee.service.EmployeeServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class EmployeeRouter {

	@Bean
	@RouterOperations({ @RouterOperation(path = "/findAll", produces = {MediaType.APPLICATION_JSON_VALUE }, 
							beanClass = EmployeeServiceImpl.class, method = RequestMethod.GET, beanMethod = "findAll", 
							operation = @Operation(operationId = "findAll", responses = {
					@ApiResponse(responseCode = "200", description = "successful operation", 
							content = @Content(schema = @Schema(implementation = Employee.class)))
							})),
		
						@RouterOperation(path = "/get-by/{id}", produces = {MediaType.APPLICATION_JSON_VALUE }, 
						beanClass = EmployeeServiceImpl.class, method = RequestMethod.GET, beanMethod = "findById", 
						operation = @Operation(operationId = "findById", responses = {
					@ApiResponse(responseCode = "200", description = "successful operation", 
							content = @Content(schema = @Schema(implementation = Employee.class))),
					@ApiResponse(responseCode = "400", description = "Invalid Employee details supplied") }, 
							parameters = {@Parameter(in = ParameterIn.PATH, name = "id") }))
						})
	public RouterFunction<ServerResponse> employeesRoute(EmployeesHandler handler) {
		return route().nest(path(""), builder -> builder.GET("/findAll", handler::findAll))
				.GET("/get-by/{id}", handler::findById).build();
	}
}
