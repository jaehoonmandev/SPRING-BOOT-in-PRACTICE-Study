package com.manning.sbip.ch08.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.manning.sbip.ch08.model.Course;

import reactor.core.publisher.Flux;

//리액티브 몽고 DB에 특화된 스프링 데이터 리포지터리인 ReactiveMongoRepository를 상속 받는다.
@Repository
public interface CourseRepository extends ReactiveMongoRepository<Course, String> {
	//ReactiveMongoRepository는 대부분 Flux, Mono, Publisher 인스턴스를 반환한다.

	//주어진 카테고리에 해당하는 모든 과정을 담은 Flux를 반환한다.
	Flux<Course> findAllByCategory(String category);
}
