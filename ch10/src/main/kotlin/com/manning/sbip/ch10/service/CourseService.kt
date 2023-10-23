package com.manning.sbip.ch10.service

import com.manning.sbip.ch10.model.Course

interface CourseService {
    //Kotlin은 fun 키워드로 함수를 선언한다.
    //return 형은 함수 뒤 : [return type]
    fun createCourse(course: Course): Course
    fun findCourseById(courseId: Long): Course
    fun findAllCourses(): Iterable<Course>
    fun updateCourse(courseId: Long, updatedCourse: Course): Course
    fun deleteCourseById(courseId: Long)
}