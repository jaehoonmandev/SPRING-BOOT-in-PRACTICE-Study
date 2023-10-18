package com.manning.sbip.ch08.config;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.manning.sbip.ch08.component.CourseHandler;

//함수형 엔드포인트 방식.
@Configuration
public class RouterContext {

	//라우터에 요청이 들어오면 이를 처리해줄 CourseHandler 핸들러를 지정해준다.
	@Bean
	RouterFunction<ServerResponse> routes(CourseHandler courseHandler) {
		return route(GET("/courses").and(accept(APPLICATION_JSON)), courseHandler::findAllCourses)
				.andRoute(GET("/courses/{id}").and(accept(APPLICATION_JSON)), courseHandler::findCourseById)
				.andRoute(POST("/courses").and(accept(APPLICATION_JSON)), courseHandler::createCourse)
				.andRoute(PUT("/courses").and(accept(APPLICATION_JSON)), courseHandler::updateCourse)
				.andRoute(DELETE("/courses/{id}").and(accept(APPLICATION_JSON)), courseHandler::deleteCourse)
				.andRoute(DELETE("/courses").and(accept(APPLICATION_JSON)), courseHandler::deleteAllCourses);
	}

}
