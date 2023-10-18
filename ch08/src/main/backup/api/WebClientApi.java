package com.manning.sbip.ch08.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.manning.sbip.ch08.model.Course;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// 스프링 웹플럭스는 HTTP 요청을 보낼 수 있는 WebClient가 포함되어있다.
// 스레드나 동시성을 직접 처리하지 않고 비동기 로직을 조합할 수 있게해주는 리엑터 기반의 함수형 평문형 API이다.
@Component
public class WebClientApi {

	private static final String BASE_URL = "http://localhost:8080/courses/";

	private WebClient webClient;

	public WebClientApi() {
		this.webClient = WebClient.builder()
				.baseUrl(BASE_URL)// baseurl을 지정하여 상대경로로 호출 할 수 있도록.
				.build();
	}

	public Mono<ResponseEntity<Course>> postNewCourse(Course course) {
		return this.webClient.post()//WebClient로 POST 요청을 보낸다
				.body(Mono.just(course), Course.class).retrieve().toEntity(Course.class)
				.doOnSuccess(result -> System.out.println("POST " + result));
	}

	public Mono<Course> updateCourse(String id, String name, String category, int rating, String description) {
		return this.webClient.put()
				.uri("{id}", id)// 파라미터 지정.
				.body(Mono.just(Course.builder().id(id).name(name).category(category).rating(rating)
						.description(description).build()), Course.class)
				.retrieve().bodyToMono(Course.class)
				.doOnSuccess(result -> System.out.println("Update Course: " + result));
	}

	public Mono<Course> getCourseById(String id) {
		return this.webClient.get().uri("{id}", id).retrieve().bodyToMono(Course.class)
				.doOnSuccess(c -> System.out.println(c)).doOnError((e) -> System.err.println(e.getMessage()));
	}

	public Flux<Course> getAllCourses() {
		return this.webClient.get().retrieve().bodyToFlux(Course.class).doOnNext(c -> System.out.println(c))
				.doOnError((e) -> System.err.println(e.getMessage()));
	}

	public Mono<Void> deleteCourse(String id) {
		return this.webClient.delete().uri("{id}", id).retrieve().bodyToMono(Void.class)
				.doOnSuccess(result -> System.out.println("DELETE " + result))
				.doOnError((e) -> System.err.println(e.getMessage()));
	}
}
