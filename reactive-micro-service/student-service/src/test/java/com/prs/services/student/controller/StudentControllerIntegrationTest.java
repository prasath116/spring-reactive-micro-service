package com.prs.services.student.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.prs.services.student.entity.StudentEntity;
import com.prs.services.student.model.Student;
import com.prs.services.student.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@TestMethodOrder(OrderAnnotation.class)
public class StudentControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @Autowired
    private StudentRepository studentRepository;
    
    static Long testId;
    
//    @BeforeEach
    void setUp() {
    	var students = List.of(new StudentEntity(null,"Prasath",31,1l,1l),
        		new StudentEntity(null,"Mani",62,1l,1l),
        		new StudentEntity(null,"Nishanthi",30,1l,1l));

    	studentRepository
                .deleteAll()
                .thenMany(studentRepository.saveAll(students))
                .blockLast();
    }
    
    @Test
    @Order(1)
    void getAllStudents() {
        webTestClient
                .get()
                .uri("/findAll")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Student.class)
                .consumeWith(l->{
                	l.getResponseBody().forEach(s->{
                		System.out.println("id : "+s.getId());
                	});
                })
                .hasSize(4);
    }

    @Test
    @Order(2)
    void addStudent() {
    	var student = new StudentEntity(null,"Prasath",31,1l,1l);
        webTestClient
                .post()
                .uri("/add")
                .bodyValue(student)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Student.class)
                .consumeWith(result -> {
    				var s = result.getResponseBody();
    				assert s != null;
    				assertNotEquals(null, s);
    				assertEquals("Prasath", s.getName());
    				testId = s.getId();
    				System.out.println("testId received : "+testId);
    				assertNotEquals(null, testId);
    			});
    }
    
    @Test
    @Order(3)
	void getStudentById() {
    	System.out.println("testId in getStudentById: "+testId);
		webTestClient
			.get()
			.uri("/get-by/{id}", testId)
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.expectBody(Student.class)
			.consumeWith(result -> {
				var student = result.getResponseBody();
				assert student != null;
				assertEquals("Prasath", student.getName());
			});
			//.expectBody() .jsonPath("$.name").isEqualTo("Prasath");

	}
    
    @Test
    @Order(4)
    void updateStudent() {
        var updatedStudent = new StudentEntity(testId,"Nishanthi M",30,2l,2l);

        webTestClient
                .put()
                .uri("/update/{id}", testId)
                .bodyValue(updatedStudent)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Student.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var student = movieInfoEntityExchangeResult.getResponseBody();
                    assert student != null;
                    assertEquals("Nishanthi M", student.getName());
                });
    }

    @Test
    @Order(5)
    void updateStudent_notFound() {
        var id = 5l;
        var updatedStudent = new StudentEntity(testId,"Nishanthi M",30,2l,2l);

        webTestClient
                .put()
                .uri("/update/{id}", id)
                .bodyValue(updatedStudent)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
    
    @Test
    @Order(6)
    void deleteStudentById() {
        webTestClient
                .delete()
                .uri("/delete/{id}", testId)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
    
}
