package com.prs.services.college.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.prs.services.college.entity.CollegeEntity;

import reactor.core.publisher.Mono;


public interface CollegeRepository extends ReactiveCrudRepository<CollegeEntity, Long>{
	public Mono<CollegeEntity> findById(Long id);
}
