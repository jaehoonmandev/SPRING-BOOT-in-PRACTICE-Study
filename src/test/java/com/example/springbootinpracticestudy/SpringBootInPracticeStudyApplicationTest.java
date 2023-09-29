package com.example.springbootinpracticestudy;

import com.example.springbootinpracticestudy.Model.Course;
import com.example.springbootinpracticestudy.Repository.CourseRepository;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SpringBootInPracticeStudyApplicationTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void givenDataAvailableWhenLoadFirstPageThenGetFiveRecords() {
        Pageable pageable = PageRequest.of(0,5); // 0페이지, 페이지당 5건 데이터.
        assertThat(courseRepository.findAll(pageable)).hasSize(5);
        assertThat(pageable.getPageNumber()).isEqualTo(0);

        Pageable nextPageable = pageable.next(); // 다음 페이지.
        assertThat(courseRepository.findAll(nextPageable)).hasSize(4);
        assertThat(nextPageable.getPageNumber()).isEqualTo(1);
    }

    @Test
    void givenDataAvailableWhenSortsFirstPageThenGetSortedSData() {
        //이름 기준 오름차순
        Pageable pageable = PageRequest.of(0,5, Sort.by(Sort.Order.asc("Name")));

        //조건
        Condition<Course> sortedFirstCourseCondition = new Condition<Course>() {
            @Override
            public boolean matches(Course course) {
                //넣은 데이터 정렬 됐는지 확인.
                return course.getId() == 4 && course.getName().equals("Cloud Native Spring Boot Application Development");
            }
        };
        assertThat(courseRepository.findAll(pageable)).first().has(sortedFirstCourseCondition);
    }

    @Test
    void givenDataAvailableWhenApplyCustomSortThenGetSortedResult() {
        //점수 내림차순, 이름 오름차순 정렬
        Pageable customSortPageable = PageRequest.of(0,5, Sort.by("Rating").descending().and(Sort.by("Name")));
        Condition<Course> customSortFirstCourseCondition = new Condition<Course>() {
            @Override
            public boolean matches(Course course) {
                return course.getId() == 2 && course.getName().equals("Getting Started with Spring Security DSL");
            }
        };
        assertThat(courseRepository.findAll(customSortPageable)).first().has(customSortFirstCourseCondition);
    }
}