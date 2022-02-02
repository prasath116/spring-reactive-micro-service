package com.prs.services.college.service;

import com.prs.services.college.entity.CollegeEntity;
import com.prs.services.college.model.College;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICollegeService {

	Mono<College> save(CollegeEntity college);

	Flux<College> findAll();

	Mono<College> findById(Long id);

}
