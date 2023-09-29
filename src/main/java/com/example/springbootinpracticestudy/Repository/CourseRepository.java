package com.example.springbootinpracticestudy.Repository;

import com.example.springbootinpracticestudy.Model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

//JPA는 메서드 이름에 따른 커스텀 쿼리를 제공한다.
public interface CourseRepository extends CrudRepository<Course, Long> {


    Iterable<Course> findAllByCategory(String category);

    // findAll : 쿼리패턴
    // By : 구분자
    // Category : 서술어 - 찾을 컬럼
    // OrderByName : 서술어 - 정렬할 기준
    // (String category) : 조건
    Iterable<Course> findAllByCategoryOrderByName(String category);

    boolean existsByName(String name);

    long countByCategory(String category);

    Iterable<Course> findByNameOrCategory(String name, String category);

    Iterable<Course> findByNameStartsWith(String name);

    Stream<Course> streamAllByCategory(String category);

}
