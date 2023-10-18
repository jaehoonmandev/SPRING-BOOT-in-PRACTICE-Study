package com.manning.sbip.ch08.controller;

import java.time.Duration;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.manning.sbip.ch08.model.Course;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class CourseController {

	//요청 - 응답 패턴
	//요청자가 정보를 전송하면 엔드포인트는 사용자에게 정보를 반환해준다.
	@MessageMapping("request-response")
	public Mono<Course> requestResponse(final Course course) {
		log.info("Received request-response course details {} ", course);
		return Mono.just(new Course("Your course name: " + course.getCourseName())); //결과를 리턴한다.
	}

	// 실행 후 망각 패턴.
	// 요청자가 정보를 전송하면 반환을 기대하지 않으며 엔드포인트는 빈 Mono 객체를 반환한다.
	@MessageMapping("fire-and-forget")
	public Mono<Void> fireAndForget(final Course course) {
		log.info("Received fire-and-forget course details {} ", course);
		return Mono.empty(); // 빈 Mono 객체를 반환함으로써 요청자는 정보를 받지 않는다.
	}

	// 요청 - 스트림 패턴
	// 요청자가 정보를 전송하면 엔드포인트는 데이터를 수정해서 1초 단위로 연달아 반환
	@MessageMapping("request-stream")
	public Flux<Course> requestStream(final Course course) {
		log.info("Received request-stream course details {} ", course);
		return Flux.interval(Duration.ofSeconds(1))// 주기를 1초마다로 설정.
				.map(index -> new Course("Your course name: " + course.getCourseName() + ". Response #" + index))
				.log();
	}

	// 채널 패턴
	// 요청자가 스트림을 전송하면 엔드포인트는 데이터를 수정해서 호출자가 지정한 시간 단위로 연달아 반환.
	// 요청자는 Flux의 delayElements() 메서드를 사용해서 시가능ㄹ 지정.
	// 요청자와 엔드포인트 모두 스트림 데이터를 전송 가능.
	@MessageMapping("stream-stream")
	public Flux<Course> channel(final Flux<Integer> settings) {
		log.info("Received stream-stream (channel) request... ");
		
		return settings.doOnNext(setting -> log.info("Requested interval is {} seconds", setting))
				.doOnCancel(() -> log.warn("Client cancelled the channel"))
				.switchMap(setting ->
						Flux.interval(Duration.ofSeconds(setting)) // 사용자가 지정한 시간을 주기로.
								.map(index -> new Course("Spring. Response #"+index)))
				.log();
	}
}
