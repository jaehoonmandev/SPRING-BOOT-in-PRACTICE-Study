package com.manning.sbip.wartowildfly.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.manning.sbip.wartowildfly.model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

}
