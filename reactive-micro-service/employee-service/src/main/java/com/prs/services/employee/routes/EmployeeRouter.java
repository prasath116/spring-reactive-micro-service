package com.prs.services.employee.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.prs.services.employee.handler.EmployeesHandler;

@Configuration
public class EmployeeRouter {

    @Bean
    public RouterFunction<ServerResponse> employeesRoute(EmployeesHandler handler) {
        return route()
                .nest(path(""), builder ->
                        builder
                                .GET("/findAll", handler::findAll))
                .GET("/get-by/{id}", handler::findById)
                .build();
    }
}
