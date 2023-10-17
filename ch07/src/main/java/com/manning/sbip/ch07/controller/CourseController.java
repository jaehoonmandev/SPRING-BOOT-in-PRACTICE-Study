package com.manning.sbip.ch07.controller;

import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manning.sbip.ch07.model.Course;
import com.manning.sbip.ch07.service.CourseService;

//요청이 들어올 시 처리할 서비스 계층

// RESTful API 역할을 수행하기 위해 Controller가 아닌 RestController를 지정.
// 이는 @Controller와 @ResponseBody를 합쳐놓은 메타 애너테이션
// @ResponseBody 애너테이션을 붙이면 반환값이 HTTP 응답 본문에 바인딩된다.
@RestController
@RequestMapping("/courses/") //API 엔드포인트 지정.
public class CourseController {
	
	private CourseService courseService;
	
	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	//'/courses/' 의 엔드포인트로 GET만 요청했을 시에는 모든 코스 정보를 제공한다.
	@GetMapping
	public Iterable<Course> getAllCourses() {
		return courseService.getCourses();
	}

	//'/courses/{id}' 경로 변수를 지정.
	@GetMapping("{id}")
	public Optional<Course> getCourseById(@PathVariable("id") long courseId) {
		return courseService.getCourseById(courseId);
	}
	
	@GetMapping("category/{name}")
	public Iterable<Course> getCourseByCategory(@PathVariable("name") String category) {
		return courseService.getCoursesByCategory(category);
	}

	//HTTP POST 요청은 요청 본문(payload)가 있어야한다.
	//이를 위해서 	@RequestBody로 요청 본문을 바인딩한다.
	@PostMapping
	public Course createCourse(@Valid @RequestBody Course course) {
		return courseService.createCourse(course);
	}
	
	@PutMapping("{id}")
	public void updateCourse(@PathVariable("id") long courseId, @Valid @RequestBody Course course) {
		courseService.updateCourse(courseId, course);
	}
	
	@DeleteMapping("{id}")
	void deleteCourseById(@PathVariable("id") long courseId) {
		courseService.deleteCourseById(courseId);
	}
	
	@DeleteMapping
	void deleteCourses() {
		courseService.deleteCourses();
	}

}
