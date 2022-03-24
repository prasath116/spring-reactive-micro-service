package com.prs.services.student.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
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
public class StudentControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @Autowired
    private StudentRepository studentRepository;
    
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
	void getStudentById() {
		Long id = 1l;
		webTestClient
			.get()
			.uri("/get-by/{id}", id)
			.exchange()
			.expectStatus()
			.is2xxSuccessful()
			.expectBody(Student.class)
			.consumeWith(result -> {
				var student = result.getResponseBody();
				assert student != null;
				assertEquals("aa", student.getName());
			});
			//.expectBody() .jsonPath("$.name").isEqualTo("Prasath");

	}
    
    @Test
    void updateStudent() {
    	var id = 2l;
        var updatedStudent = new StudentEntity(2l,"Nishanthi M",30,2l,2l);

        webTestClient
                .put()
                .uri("/update/{id}", id)
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
    void updateStudent_notFound() {
        var id = 5l;
        var updatedStudent = new StudentEntity(2l,"Nishanthi M",30,2l,2l);

        webTestClient
                .put()
                .uri("/update/{id}", id)
                .bodyValue(updatedStudent)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
    
    @Test
    void deleteStudentById() {
    	 var id = 2l;

        webTestClient
                .delete()
                .uri("/delete/{id}", id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
    
}
