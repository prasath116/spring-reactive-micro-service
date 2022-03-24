package com.prs.services.student.cucumberConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.prs.services.student.entity.StudentEntity;
import com.prs.services.student.model.Student;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StudentCucumberSteps {

	private final Logger log = LoggerFactory.getLogger(StudentCucumberSteps.class);
	StudentEntity studentTest = new StudentEntity(null, "testCucumber", 25, 2l, 2l);
	@Autowired
	private WebTestClient webTestClient;

	@Given("User is on Home Page")
	public void user_is_on_home_page() {
		System.out.println("user_is_on_home_page");
        webTestClient
                .get()
                .uri("/findAll")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
	}

	@When("I add a student to server")
	public void i_add_a_student_to_server() {
		System.out.println("i_add_a_student_to_server");
		webTestClient.post().uri("/add")
        .bodyValue(studentTest)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Student.class)
        .consumeWith(result -> {
            var stud = result.getResponseBody();
            assert  stud!=null;
            String expectedName = "testCucumber";
            assertEquals(expectedName, stud.getName());

        });
	}

	@Then("the server should contain that student")
	public void the_server_should_contain_that_student() {
		System.out.println("the_server_should_contain_that_student");
		webTestClient
        .get()
        .uri("/findAll")
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(Student.class)
        .consumeWith(l->{
        	var opt = l.getResponseBody().stream().filter(s-> s.getName().equals("testCucumber")).findAny();
        	assert opt.isPresent();
        });
	}

}
