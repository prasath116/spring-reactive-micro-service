package com.prs.services;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;

import com.prs.services.employee.entity.EmployeeEntity;
import com.prs.services.employee.repository.EmployeeRepository;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableWebFlux
@Slf4j
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

//	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

		return initializer;
	}
	
//  @Bean
  public CommandLineRunner demo(EmployeeRepository repository) {

      return (args) -> {
          // save a few Employees
    	  repository.save(new EmployeeEntity(null,"prasath", 35, "pos1",1l,1l)).block(Duration.ofSeconds(10));
          repository.saveAll(Arrays.asList(new EmployeeEntity(null,"aa", 35, "pos1",1l,1l)))
              .blockLast(Duration.ofSeconds(10));

          // fetch all Employees
          log.info("Customers found with findAll():");
          log.info("-------------------------------");
          repository.findAll().doOnNext(customer -> {
              log.info(customer.toString());
          }).blockLast(Duration.ofSeconds(10));

          log.info("");

          // fetch an individual Employee by ID
			repository.findById(1L).doOnNext(customer -> {
				log.info("Customer found with findById(1L):");
				log.info("--------------------------------");
				log.info(customer.toString());
				log.info("");
			}).block(Duration.ofSeconds(10));
			
			repository.findByName("prasath").doOnNext(customer -> {
				log.info("Employee found with findbyName(prasath):");
				log.info("--------------------------------");
				log.info(customer.toString());
				log.info("");
			}).blockFirst(Duration.ofSeconds(10));


      };
  }
	
}
