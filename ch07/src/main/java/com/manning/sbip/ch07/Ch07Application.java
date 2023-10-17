package com.manning.sbip.ch07;

import com.manning.sbip.ch07.model.Course;
import com.manning.sbip.ch07.repository.CourseRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true) //메서드 수준 시큐리티를 사용하기 위한.
public class Ch07Application  {

    public static void main(String[] args) {
        SpringApplication.run(Ch07Application.class, args);
    }


    /*@Bean
    public OpenAPI customOpenAPI(@Value("${app.description}") String appDescription,
                                 @Value("${app.version}") String appVersion) {

        return new OpenAPI().info(new Info().title("Course Tracker API").version(appVersion)
                .description(appDescription).termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }*/

    @Bean
    CommandLineRunner createCourse(CourseRepository courseRepository) {
        return (args) -> {
            Course spring = new Course(null, "Spring", "john");
            Course python = new Course(null, "Python", "steve");
            courseRepository.save(spring);
            courseRepository.save(python);

        };
    }
}
