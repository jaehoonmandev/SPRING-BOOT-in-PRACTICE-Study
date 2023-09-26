package com.example.springbootinpracticestudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//스프링 부트 애플리케이션 시작 시 코드 실행
@SpringBootApplication
public class CourseTrackerApplication {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String args[]){
        SpringApplication.run(CourseTrackerApplication.class,args);
    }
    //Main 클래스에서 CommanLineRunner 구현하기
//    @Override
//    public void run(String... args) throws Exception {
//        logger.info("CourseTrackerApplication CommandLineRunner has executed");
//    }


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
