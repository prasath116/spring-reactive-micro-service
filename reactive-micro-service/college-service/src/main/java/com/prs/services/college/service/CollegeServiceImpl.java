package com.prs.services.college.service;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.services.college.entity.CollegeEntity;
import com.prs.services.college.model.College;
import com.prs.services.college.repository.CollegeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CollegeServiceImpl implements ICollegeService {
	@Autowired
	private CollegeRepository repository;

	@Override
	public Mono<College> save(CollegeEntity college) {
		return repository.save(college).map(mapper);
	}

	@Override
	public Flux<College> findAll() {
		return repository.findAll().map(mapper);//.collect(Collectors.toList());
	}

	@Override
	public Mono<College> findById(Long id) {
		return repository.findById(id).map(mapper);
	}

	private Function<? super CollegeEntity, ? extends College> mapper = c -> {
		College response = new College();
		BeanUtils.copyProperties(c, response);
		return response;
	};
}
