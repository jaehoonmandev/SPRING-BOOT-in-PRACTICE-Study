package com.example.springbootinpracticestudy;

import com.example.springbootinpracticestudy.Model.Course;
import com.example.springbootinpracticestudy.Repository.AuthorRepository;
import com.example.springbootinpracticestudy.Repository.CourseRepository;
import com.example.springbootinpracticestudy.ibp.DescriptionOnly;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBootInPracticeStudyApplicationTest {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void whenCountAllCoursesThenExpectFiveCourses() {
        assertThat(authorRepository.getAuthorCourseInfo(2)).hasSize(3);
    }

    @Test
    public void givenACourseAvailableWhenGetCourseByNameThenGetCourseDescription() {
        Iterable<DescriptionOnly> result = courseRepository.getCourseByName("Rapid Spring Boot Application Development");
        assertThat(result)
                .extracting("description").contains("Spring Boot gives all the power of the Spring Framework without all of the complexity");
    }
}