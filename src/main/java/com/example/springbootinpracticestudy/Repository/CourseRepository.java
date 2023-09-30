package com.example.springbootinpracticestudy.Repository;

import com.example.springbootinpracticestudy.Model.Course;
import com.example.springbootinpracticestudy.ibp.DescriptionOnly;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    Iterable<DescriptionOnly> getCourseByName(String name);
}