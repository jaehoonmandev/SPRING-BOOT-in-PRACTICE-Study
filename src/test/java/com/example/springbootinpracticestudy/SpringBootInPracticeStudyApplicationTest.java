package com.example.springbootinpracticestudy;

import com.example.springbootinpracticestudy.Model.Course;
import com.example.springbootinpracticestudy.Repository.CourseRepository;
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

    //CriteriaBuilder를 사용하기 위한 인스턴스 주입, Criteria API 기반 쿼리, 조회, 정렬 등을 사용.
    //라이프사이클은 퍼시스턴스 컨텍스트에 의해 관리.
    @Autowired
    private EntityManager entityManager;

    @Test
    public void givenCoursesCreatedWhenLoadCoursesWithQueryThenExpectCorrectCourseDetails() {
        courseRepository.saveAll(getCourseList());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //타입 안정성 확보
        CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);

        //결론적으로 조회 조건은 Predicate 타입
        Predicate courseCategoryPredicate = criteriaBuilder.equal(courseRoot.get("category"), "Spring");

        //CriteriaQuery를 사용하게되면 from(),where(), groupBy(), orderBy() 등 메서드를 사용하여 쿼리 작성 가능.
        courseCriteriaQuery.where(courseCategoryPredicate);
        TypedQuery<Course> query = entityManager.createQuery(courseCriteriaQuery);
        assertThat(query.getResultList().size()).isEqualTo(3);

    }

    private List<Course> getCourseList() {
        Course rapidSpringBootCourse = new Course("Rapid Spring Boot Application Development", "Spring", 4,"Spring Boot gives all the power of the Spring Framework without all of the complexity");
        Course springSecurityDslCourse = new Course("Getting Started with Spring Security DSL", "Spring", 5, "Learn Spring Security DSL in easy steps");
        Course springCloudKubernetesCourse = new Course("Getting Started with Spring Cloud Kubernetes", "Spring", 3, "Master Spring Boot application deployment with Kubernetes");
        Course rapidPythonCourse = new Course("Getting Started with Python", "Python", 5, "Learn Python concepts in easy steps");
        Course gameDevelopmentWithPython = new Course("Game Development with Python", "Python", 2, "Learn Python by developing 10 wonderful games");
        Course javaScriptForAll = new Course("JavaScript for All", "JavaScript", 4, "Learn basic JavaScript syntax that can apply to anywhere");
        Course javaScriptCompleteGuide = new Course("JavaScript Complete Guide", "JavaScript", 5, "Master JavaScript with Core Concepts and Web Development");

        return Arrays.asList(rapidSpringBootCourse, springSecurityDslCourse, springCloudKubernetesCourse, rapidPythonCourse, gameDevelopmentWithPython, javaScriptForAll, javaScriptCompleteGuide);
    }
}