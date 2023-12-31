package com.manning.sbip.ch08.component;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.manning.sbip.ch08.model.Course;
import com.manning.sbip.ch08.repository.CourseRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//
@Component
public class CourseHandler {

	private CourseRepository courseRepository;

	@Autowired
	public CourseHandler(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	//모든 과정을 조회하는 핸들러
	//ServerRequest는 클라이언트의 요청을 담고 있는 서버 측 HTTP 요청 객체
	public Mono<ServerResponse> findAllCourses(ServerRequest serverRequest) {
		Flux<Course> courses = this.courseRepository.findAll(); // 모든 과정을 조회하여

		//Content-type이 application/json이므로 타입을 APPLICATION_JSON로 지정하여 ServerResponse(서버 측 응답 객체)에 담는다.
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(courses, Course.class);
	}

	public Mono<ServerResponse> findCourseById(ServerRequest serverRequest) {
		String courseId = serverRequest.pathVariable("id");
		Mono<Course> courseMono = this.courseRepository.findById(courseId);
		return courseMono.flatMap(course -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(course)))
				.switchIfEmpty(notFound());
	}

	public Mono<ServerResponse> createCourse(ServerRequest serverRequest) {
		//ServerRequest의 bodyToMono 의 기능을 이용하여 요청 본문 추출.
		Mono<Course> courseMono = serverRequest.bodyToMono(Course.class);

		//추출된 본문 내용은 새로운 데이터를 생성하는데 사용된다.
		return courseMono.flatMap(course -> ServerResponse.status(HttpStatus.CREATED).contentType(APPLICATION_JSON)
				.body(this.courseRepository.save(course), Course.class));
	}

	public Mono<ServerResponse> updateCourse(ServerRequest serverRequest) {
		String courseId = serverRequest.pathVariable("id");
		Mono<Course> existingCourseMono = this.courseRepository.findById(courseId);
		Mono<Course> newCourseMono = serverRequest.bodyToMono(Course.class);
		return newCourseMono
				.zipWith(existingCourseMono,
						(newCourse, existingCourse) ->
								Course.builder().id(existingCourse.getId())
									.name(newCourse.getName())
									.category(newCourse.getCategory())
									.rating(newCourse.getRating())
									.description(newCourse.getDescription()).build())
				.flatMap(course -> ServerResponse.ok().contentType(APPLICATION_JSON)
						.body(this.courseRepository.save(course), Course.class))
				.switchIfEmpty(notFound());
	}

	public Mono<ServerResponse> deleteCourse(ServerRequest serverRequest) {
		String courseId = serverRequest.pathVariable("id");
		return this.courseRepository.findById(courseId)
				.flatMap(existingCourse -> ServerResponse.ok().build(this.courseRepository.deleteById(courseId)))
				.switchIfEmpty(notFound());
	}

	public Mono<ServerResponse> deleteAllCourses(ServerRequest serverRequest) {
		return ServerResponse.ok().build(this.courseRepository.deleteAll());
	}

	private Mono<ServerResponse> notFound() {
		return ServerResponse.notFound().build();
	}

}
