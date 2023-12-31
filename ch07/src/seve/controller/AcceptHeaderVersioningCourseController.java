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

@RestController
@RequestMapping("/courses/")
public class AcceptHeaderVersioningCourseController {

	private CourseService courseService;
	private ModernCourseRepository modernCourseRepository;

	@Autowired
	public AcceptHeaderVersioningCourseController(CourseService courseService, ModernCourseRepository modernCourseRepository) {
		this.courseService = courseService;
		this.modernCourseRepository = modernCourseRepository;
	}

	//Accept 헤더를 이용한 버저닝 미디어 타입 버저닝이라고도 한다.
	@GetMapping(produces = "application/vnd.sbip.app-v1+json")
	@ResponseStatus(code = HttpStatus.OK)
	public Iterable<Course> getAllLegacyCourses() {
		return courseService.getCourses();
	}

	@PostMapping(produces = "application/vnd.sbip.app-v1+json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Course createCourse(@Valid @RequestBody Course course) {
		return courseService.createCourse(course);
	}

	@GetMapping(produces = "application/vnd.sbip.app-v2+json")
	@ResponseStatus(code = HttpStatus.OK)
	public Iterable<ModernCourse> getAllModernCourses() {
		return modernCourseRepository.findAll();
	}

	@PostMapping(produces = "application/vnd.sbip.app-v2+json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ModernCourse createCourse(@Valid @RequestBody ModernCourse modernCourse) {
		return modernCourseRepository.save(modernCourse);
	}
}
