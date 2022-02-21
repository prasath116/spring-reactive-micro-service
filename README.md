
# Spring Cloud Reactive Microservices sample
To learn Spring Cloud Microservices and other spring modules, I took code from few samples available in net and clubbed together and pushed it into my git repo to avoid data loss. 

## Environment Details
	Maven, Java11, Spring, Spring boot

## Architecture
Our sample microservice application having following modules:
### 1) Config Server
- **config-service** 
	- A Spring Boot application which uses Spring Cloud Config Server. *@EnableConfigServer* makes the application as Cloud Config Server. This server should be started first to make other services configuration files ready.
	- It has environment specific configuration files for other services(discovery-service,student-service, etc). Those files should be in git repositary to utilize the actual purpose of config server. Is there any change in properties, we no need to restart any of the servers. Need to change the properties & push to git repo. After successfull commit we need to reload cache from our business service, where this change should be reflect. 
	- In My case I updated *environment.details* property in *department-service*. 
	Beans which are using that properties should be annotated with *@RefreshScope* and to reload the cache we need to hit **/actuator/refresh** with post method. We can understand better by going through *department-service's* dependencies & **DepartmentController** file. 

<img src="https://github.com/prasath116/spring-reactive-micro-service/blob/master/readme-images/ConfigServer.png" title="Config server setup"><br/>
### 2) Netflix Eureka server
- **discovery-service** - A Spring Cloud Netflix Eureka embedded discovery server. *@EnableEurekaServer* makes the application as Eureka embedded discovery server and in config file need to ensure *eureka.client.registerWithEureka = false,    eureka.client.fetchRegistry = false*
<img src="https://github.com/prasath116/spring-reactive-micro-service/blob/master/readme-images/DiscoveryServerConf.png" title="Discovery server setup"><br/>
### 3) DiscoveryClients or Netflix Eureka clients
*@EnableDiscoveryClient* makes the application as Discovery Client where this will be registered with our Netflix Eureka server by adding this config eureka.client.serviceUrl.defaultZone=http://localhost:8061/eureka/, **http://localhost:8061** url is our Eureka server url. Following services are Eureka clients which are registered with our *discovery-service*.
<img src="https://github.com/prasath116/spring-reactive-micro-service/blob/master/readme-images/DiscoveryClientConf.png" title="Discovery client setup"><br/>
- **gateway-service** - A Spring Boot application that acts as a gateway in our architecture, which recives requests from clients and routes to appropriate services in our micro service.
- **employee-service** -  A Spring Boot application that allows to perform CRUD operation on h2 db using spring data r2dbc of employees.
- **student-service** - A Spring Boot application that allows to perform CRUD operation on h2 db using spring data r2dbc of students.
- **department-service** -  A Spring Boot application that allows to perform CRUD operation on h2 db using spring data r2dbc of departments. It communicates with employee-service. 
- **college-service** -  A Spring Boot application that allows to perform CRUD operation on h2 db using spring data r2dbc of organizations. It communicates with both employee-service and department-service.
Here is the Discovery server dashboard. Instances currently registered with our Eureka Discovery server and their statuses
<img src="https://github.com/prasath116/spring-reactive-micro-service/blob/master/readme-images/DiscoveryServer.png" title="Server status"><br/>

### 4) Custom lib
- **common-utils** - Logger aspect is common for all service. So Generic logger aspect library designed & added in all the services. In future any common things needed for all the business services, we can add it in this common-utils and we can make use of it.

## Tools and Technologies used
	- Java 11
	- Lombok pluging 
	- Maven
	- Spring
### In Spring following modules we used to explore those 
- **AOP** : To log the request/ response from client and request/response which we sent to other external server.
- **Actuator** : To enable **/actuator/refresh** for relaoding cache. And there are lots of endpoints are there in spring we can check here [actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html).
- **Cloud** : For over all microservice architecture.
- **Data r2dbc** : Similar to spring data jpa which fetches the data in reactive way. Since we are using web-flux(non-blocling), we should not go with normal Spring data jpa.
There is an issue with ReactiveCrudRepository's *save* method from *spring-boot-starter-data-r2dbc* library. So I used *saveAll* method for even single entity save operation.
 All are small service with single table, so we didn't explore one-one, one-many, many-one, many-many mappings.
<img src="https://github.com/prasath116/spring-reactive-micro-service/blob/master/readme-images/R2DbcConfig.png" title="R2Dbc config"><br/>
- **Exception Handler** : Yet to do.
- **H2 DB** : Instead of keeping in cache, I used h2 DB to learn basics in spring data r2dbc. Instead of keeping as inmemory DB I stored it in local file to avoid data loss.
- **Logs** : Logback used to log.
- **Profile** : Spring profile used for different environment configuration. Also to run our application we need to add env valiable as shown.
	- *spring.profiles.active* dev or prod. Based on profile we have done logics in department-service
	- *username* db username.
	- *password* db password. Db credentials should be from environment variables for security purpose.
			<img src="https://github.com/prasath116/spring-reactive-micro-service/blob/master/readme-images/EnvVariables.PNG" title="Environment Variables"><br/>
- **Swagger** : springdoc-openapi dependency used. Should not add any dependencied other than these dependencies shown for swagger for web-flux. 
<img src="https://github.com/prasath116/spring-reactive-micro-service/blob/master/readme-images/Swagger-dependency.png" title="Swagger dependency"><br/>
Swagger Ui can be accessed via default swagger-ui endpoint /swagger-ui.html. For department-service it will be http://localhost:8060/department/swagger-ui.html . With this inbuild swager ui we can proceed our test instead of going for postman or any other test client or testing tools.
- **WebFlux** : To achieve non blocking I/O we are using spring 5 webflux & projectreactor.  Flux for more than on object as response and Mono for single object as response. We should not add *spring-boot-starter-web* instead we need to add *spring-boot-starter-webfluxFlux* dependency.
Defeult server for *spring-boot-starter-webfluxFlux* is netty, which is non-blocking server.
Defeult server for  *spring-boot-starter-web* is tomcat.
- **Functional Web** : Implemented Functional web for 2 endpoints. You can check in **EmployeeRouter & EmployeesHandler** class from **employee-service** project. Functional web is another way of coding. Currently with Functional web we cannot achieve inbuild validations & swagger-ui. So I used only for 2 endpoints. We can get more about Functional web [here](https://spring.io/blog/2016/09/22/new-in-spring-5-functional-web-framework).
- **WebClient** : Spring 5 reactive WebClient used for non blocking client call to connect to external server.
