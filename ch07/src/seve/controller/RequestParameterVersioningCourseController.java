package com.manning.sbip.ch07.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manning.sbip.ch07.model.Course;
import com.manning.sbip.ch07.model.ModernCourse;
import com.manning.sbip.ch07.repository.ModernCourseRepository;
import com.manning.sbip.ch07.service.CourseService;

//HTTP 요청 파라미터를 사용하는 API 버저닝
@RestController
@RequestMapping("/courses/")
public class RequestParameterVersioningCourseController {

	private CourseService courseService;
	private ModernCourseRepository modernCourseRepository;

	@Autowired
	public RequestParameterVersioningCourseController(CourseService courseService, ModernCourseRepository modernCourseRepository) {
		this.courseService = courseService;
		this.modernCourseRepository = modernCourseRepository;
	}

	// API 호출 시 파라미터로 버전 정보를 전달하면 params 에 맞는 버전의 기능이 동작.
	@GetMapping(params = "version=v1")
	@ResponseStatus(code = HttpStatus.OK)
	public Iterable<Course> getAllLegacyCourses() {
		return courseService.getCourses();
	}

	@PostMapping(params = "version=v1")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Course createCourse(@Valid @RequestBody Course course) {
		return courseService.createCourse(course);
	}

	@GetMapping(params = "version=v2")
	@ResponseStatus(code = HttpStatus.OK)
	public Iterable<ModernCourse> getAllModernCourses() {
		//서비스 계증으로 Repository를 호출하는게 바람직하다.
		return modernCourseRepository.findAll();
	}

	@PostMapping(params = "version=v2")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ModernCourse createCourse(@Valid @RequestBody ModernCourse modernCourse) {
		return modernCourseRepository.save(modernCourse);
	}
}
