package com.manning.sbip.ch07.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.manning.sbip.ch07.exception.CourseNotFoundException;

// 해당 애너테이션이 적용된 클래스에 작성된 예외처리는 기본적으로 모든 컨트롤러에서 발생한 예외에 적용.
// @Component 의 특별한 타입.(Bean 등록)
@ControllerAdvice
public class CourseTrackerGlobalExceptionHandler
		extends ResponseEntityExceptionHandler { // @RequestMapping 이 붙어 있는 모든 메서드에서 발생한 예외를 @ExceptionHandler가 붙어있는 메서드에서 처리할 수 있도록.

	@ExceptionHandler(value = {CourseNotFoundException.class})//@RequestMapping의 예외 처리를 담당.
	public ResponseEntity<?> handleCourseNotFound(CourseNotFoundException courseNotFoundException, WebRequest request) {
		return handleExceptionInternal( // 수퍼클래스 매서드 호출로 예외 처리
				courseNotFoundException,
				courseNotFoundException.getMessage(), new HttpHeaders(),
				HttpStatus.NOT_FOUND, //404 설정
				request);
	}
}
