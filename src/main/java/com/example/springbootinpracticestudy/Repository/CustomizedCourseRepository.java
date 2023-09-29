package com.example.springbootinpracticestudy.Repository;

import com.example.springbootinpracticestudy.Model.Course;
import org.springframework.stereotype.Repository;

@Repository // 자동감지, 예외 변환을 위한.
public interface CustomizedCourseRepository extends BaseRepository<Course, Long> {
}
