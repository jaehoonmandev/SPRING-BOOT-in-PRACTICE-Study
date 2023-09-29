package com.example.springbootinpracticestudy.Repository;

import com.example.springbootinpracticestudy.Model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CourseRepository extends CrudRepository<Course, Long> {

    //NamedQuery 로 정의한 메서드명 정의
    Iterable<Course> findAllByCategoryAndRating(String category, int rating);
}