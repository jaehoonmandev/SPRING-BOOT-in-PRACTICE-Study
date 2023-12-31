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

import com.manning.sbip.ch07.model.ModernCourse;
import com.manning.sbip.ch07.repository.ModernCourseRepository;

@RestController
@RequestMapping("/courses/v2")
public class ModernCourseController {
	
	private ModernCourseRepository modernCourseRepository; // 가격정보가 추가되면서 새로운 JPA 엔티티 클래스를 주입한다.
	
	@Autowired
	public ModernCourseController(ModernCourseRepository modernCourseRepository) {
		this.modernCourseRepository = modernCourseRepository;
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Iterable<ModernCourse> getAllCourses() {
		return modernCourseRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ModernCourse createCourse(@Valid @RequestBody ModernCourse modernCourse) {
		return modernCourseRepository.save(modernCourse);
	}
}
