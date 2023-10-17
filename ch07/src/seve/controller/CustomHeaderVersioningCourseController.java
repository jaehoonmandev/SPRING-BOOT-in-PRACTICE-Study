package com.manning.sbip.ch07.controller;

import com.manning.sbip.ch07.model.Course;
import com.manning.sbip.ch07.model.ModernCourse;
import com.manning.sbip.ch07.repository.ModernCourseRepository;
import com.manning.sbip.ch07.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses/")
public class CustomHeaderVersioningCourseController {

	private CourseService courseService;
	private ModernCourseRepository modernCourseRepository;

	@Autowired
	public CustomHeaderVersioningCourseController(CourseService courseService, ModernCourseRepository modernCourseRepository) {
		this.courseService = courseService;
		this.modernCourseRepository = modernCourseRepository;
	}

	//커스텀 헤더로 API를 구별.
	//HTTP 요청 파라미터를 사용방식과 비슷하지만 HTTP 요청에 커스텀 HTTP 헤더를 추가해서 사용한다.
	@GetMapping(headers = "X-API-VERSION=v1")
	@ResponseStatus(code = HttpStatus.OK)
	public Iterable<Course> getAllLegacyCourses() {
		return courseService.getCourses();
	}

	@PostMapping(headers = "X-API-VERSION=v1")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Course createCourse(@Valid @RequestBody Course course) {
		return courseService.createCourse(course);
	}

	@GetMapping(headers = "X-API-VERSION=v2")
	@ResponseStatus(code = HttpStatus.OK)
	public Iterable<ModernCourse> getAllModernCourses() {
		return modernCourseRepository.findAll();
	}

	@PostMapping(headers = "X-API-VERSION=v2")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ModernCourse createCourse(@Valid @RequestBody ModernCourse modernCourse) {
		return modernCourseRepository.save(modernCourse);
	}
}
