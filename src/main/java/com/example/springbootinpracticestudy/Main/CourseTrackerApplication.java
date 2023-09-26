package com.example.springbootinpracticestudy.Main;

import com.example.springbootinpracticestudy.Model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;


//스프링 부트 애플리케이션 시작 시 코드 실행 - Main 클래스에서 CommanLineRunner 구현하기
@SpringBootApplication
public class CourseTrackerApplication implements CommandLineRunner {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String args[]){
        SpringApplication.run(CourseTrackerApplication.class,args);
    }


    // @Override
//    public void run(String... args) throws Exception {
//        logger.info("CourseTrackerApplication CommandLineRunner has executed");
//
//        Course course = new Course();
//        course.setId(1);
//        course.setRating(0); // 조건에 맞지 않는 값을 주어 일부러 error 발생
//
//        //course bean 객체의 유효성을 검증하는 Validator 인스턴스 획득
//        Validator validator =
//                Validation.buildDefaultValidatorFactory().getValidator();
//
//        //제약 사항 준수 여부를 검증하고 위반 사항을 모아서 반환
//        Set<ConstraintViolation<Course>> violations =
//                validator.validate(course);
//
//        violations.forEach(courseConstraintViolation ->
//                logger.error(String.valueOf(courseConstraintViolation)));
//    }


    @Override
    public void run(String... args) throws Exception {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        User user1 = new User("sbip01", "sbip");
        Set<ConstraintViolation<User>> violations = validator.validate(user1);
        logger.error("Password for user1 do not adhere to the password policy");
        violations.forEach(constraintViolation -> logger.error("Violation details: [{}].", constraintViolation.getMessage()));

        User user2 = new User("sbip02", "Sbip01$4UDfg");
        violations = validator.validate(user2);
        if(violations.isEmpty()) {
            logger.info("Password for user2 adhere to the password policy");
        }

        User user3 = new User("sbip03", "Sbip01$4UDfgggg");
        violations = validator.validate(user3);
        logger.error("Password for user3 violates maximum repetitive rule");
        violations.forEach(constraintViolation -> logger.error("Violation details: [{}].", constraintViolation.getMessage()));

        User user4 = new User("sbip04", "Sbip014UDfgggg");
        violations = validator.validate(user4);
        logger.error("Password for user4 violates special character rule");
        violations.forEach(constraintViolation -> logger.error("Violation details: [{}].", constraintViolation.getMessage()));

    }

    //Bean 으로 등록하여 사용.
//    @Bean
//    public CommandLineRunner commandLineRunner(){
//        //  CommandLineRunner 인터페이스는 run 메서드 하나만 가지고 있는 함수형이라 람다식으로 표현.
//        return args -> {
//            logger.info("CommandLineRunner executed as a bean definition with "
//                    + args.length + "arguments");
//            for(int i = 0 ; i< args.length; i++){
//                logger.info("Argument : " + args[i]);
//            }
//        };
//    }

}
