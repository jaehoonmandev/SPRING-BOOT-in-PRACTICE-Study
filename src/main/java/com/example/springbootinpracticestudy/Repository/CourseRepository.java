package com.example.springbootinpracticestudy.Repository;

import com.example.springbootinpracticestudy.Model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;

//페이징 처리를 위한 PagingAndSortingRepository 확장
public interface CourseRepository extends PagingAndSortingRepository<Course, Long>{
}