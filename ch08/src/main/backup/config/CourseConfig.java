package com.manning.sbip.ch08.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.manning.sbip.ch08.model.Course;
import com.manning.sbip.ch08.repository.CourseRepository;

import reactor.core.publisher.Flux;

@Configuration
public class CourseConfig {

	@Bean
	public CommandLineRunner init(CourseRepository courseRepository) {
		return args -> {

			Course course1 = Course.builder().name("Mastering Spring Boot").category("Spring").rating(4)
					.description("Mastering Spring Boot").build();
			Course course2 = Course.builder().name("Mastering Python").category("Python").rating(5)
					.description("Mastering Python").build();
			Course course3 = Course.builder().name("Mastering Go").category("Go").rating(3).description("Mastering Go")
					.build();

			// Courses를 생성하고 콘솔 로그에 출력하는 과정.
			Flux.just(course1, course2, course3) // 정보를 담은 Flux를 생성
					.flatMap(courseRepository::save) // 해당 정보 저장
					.thenMany(courseRepository.findAll()) // 저장한 정보를 조회하여 Flux로 반환
					// Flux를 구독하여 각 과정을 콘솔로그에 출력.
					// 리액티브 프로그래밍은 지연 방식으로 동작하므로 subscribe() 메서드 호출 전에는 아무런 일도 발생하지 않는다.
					.subscribe(System.out::println);
		};
	}
}
