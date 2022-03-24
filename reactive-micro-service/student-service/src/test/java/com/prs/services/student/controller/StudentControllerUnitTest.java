package com.prs.services.student.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.prs.services.student.entity.StudentEntity;
import com.prs.services.student.model.Student;
import com.prs.services.student.service.IStudentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = StudentController.class)
@AutoConfigureWebTestClient
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentControllerUnitTest {
	@MockBean
    private IStudentService studentServiceMock;

    @Autowired
    private WebTestClient webTestClient;
    
    @Test
    void getAllStudents() {
        when(studentServiceMock.findAll()).thenReturn(Flux.fromIterable(mockedStudents()));
        webTestClient
                .get()
                .uri("/findAll")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Student.class)
                .hasSize(3);
    }

    @Test
	void getStudentById() {
		Long id = 1l;
		when(studentServiceMock.findById(isA(Long.class))).thenReturn(Mono.just(mockedStudents().get(0)));
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
				assertEquals("Prasath", student.getName());
			});
			//.expectBody() .jsonPath("$.name").isEqualTo("Prasath");

	}
    
    @Test
    void updateStudent() {
    	var id = 2l;
        var updatedStudent = new Student(2l,"Nishanthi M",30,null,null);

        when(studentServiceMock.update(isA(Long.class), isA(StudentEntity.class)))
                .thenReturn(Mono.just(updatedStudent));

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
        var updatedStudent = mockedStudents().get(2);

        when(studentServiceMock.update(isA(Long.class), isA(StudentEntity.class)))
                .thenReturn(Mono.empty());


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

        when(studentServiceMock.deleteById(isA(Long.class)))
                .thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri("/delete/{id}", id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
    
	private List<Student> mockedStudents() {
		var students = List.of(new Student(1l,"Prasath",31,null,null),
        		new Student(3l,"Mani",62,null,null),
        		new Student(2l,"Nishanthi",30,null,null));
		return students;
	}
}
