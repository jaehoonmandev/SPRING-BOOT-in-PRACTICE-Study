package com.example.springbootinpracticestudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootInPracticeStudyApplication {

    //로그 설정
    private static final Logger log =
            LoggerFactory.getLogger(SpringBootInPracticeStudyApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(SpringBootInPracticeStudyApplication.class);

    }

}
