package com.manning.sbip.ch07.controller;

import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.manning.sbip.ch07.model.Course;
import com.manning.sbip.ch07.service.CourseService;

//요청이 들어올 시 처리할 서비스 계층

// RESTful API 역할을 수행하기 위해 Controller가 아닌 RestController를 지정.
// 이는 @Controller와 @ResponseBody를 합쳐놓은 메타 애너테이션
// @ResponseBody 애너테이션을 붙이면 반환값이 HTTP 응답 본문에 바인딩된다.
@RestController
@RequestMapping("/courses/") //API 엔드포인트 지정.
//API를 문서화 하기 위한 애너테이션으로 컨트롤러에 대한 정보를 나타낸다.
@Tag(name = "Course Controller", description = "This REST Controller provide services to manage courses in the Course Tracker application")
public class CourseController {
	
	private CourseService courseService;
	
	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	//'/courses/' 의 엔드포인트로 GET만 요청했을 시에는 모든 코스 정보를 제공한다.

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)// API가 반환하는 상태 코드. 이를통해 API 사용자가 로직을 구성하므로 신중하게 상태 코드를 결정하자.
	@Operation(summary = "Provides all courses available in the Course Tracker application") // 엔드포인트의 목적
	public Iterable<Course> getAllCourses() {
		return courseService.getCourses();
	}

	//'/courses/{id}' 경로 변수를 지정.
	@GetMapping("{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@Operation(summary = "Provides course details for the supplied course id from the Course Tracker application")
	public Optional<Course> getCourseById(@PathVariable("id") long courseId) {
		return courseService.getCourseById(courseId);
	}
	
	@GetMapping("category/{name}")
	@ResponseStatus(code = HttpStatus.OK)
	@Operation(summary = "Provides course details for the supplied course category from the Course Tracker application")
	public Iterable<Course> getCourseByCategory(@PathVariable("name") String category) {
		return courseService.getCoursesByCategory(category);
	}

	//HTTP POST 요청은 요청 본문(payload)가 있어야한다.
	//이를 위해서 	@RequestBody로 요청 본문을 바인딩한다.
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(summary = "Creates a new course in the Course Tracker application")
	public Course createCourse(@Valid @RequestBody Course course) {
		return courseService.createCourse(course);
	}
	
	@PutMapping("{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Updates the course details in the Course Tracker application for the supplied course id")
	public void updateCourse(@PathVariable("id") long courseId, @Valid @RequestBody Course course) {
		courseService.updateCourse(courseId, course);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Deletes the course details for the supplied course id from the Course Tracker application")
	void deleteCourseById(@PathVariable("id") long courseId) {
		courseService.deleteCourseById(courseId);
	}
	
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@Operation(summary = "Deletes all courses from the Course Tracker application")
	void deleteCourses() {
		courseService.deleteCourses();
	}

}
