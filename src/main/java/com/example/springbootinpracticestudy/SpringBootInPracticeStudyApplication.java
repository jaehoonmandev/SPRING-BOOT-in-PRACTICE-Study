package com.example.springbootinpracticestudy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

/**
 * @EnableConfigurationProperties는 일일이 클래스를 넣어줘야하는대신
 * @ConfigurationPropertiesScan은 @ConfigurationProperties가 붙어있는 클래스를 모두 탐색해서
 * 스프링 컨테이너에 등록
 */
//@EnableConfigurationProperties(AppProperties.class)
@ConfigurationPropertiesScan
public class SpringBootInPracticeStudyApplication {

    //로그 설정
    private static final Logger log =
            LoggerFactory.getLogger(SpringBootInPracticeStudyApplication.class);

    public static void main(String[] args) {


        //프로젝트 초기화 시 application.properties 에서 import할 파일이 없다면 이를 무시(ignore)하도록 설정.
//        Properties properties = new Properties();
//        properties.setProperty("spring.config.on-not-found", "ignore");
//
//
//        /**
//         * SpringApplication 클래스를 사용하면 애플리케이션 설정 정보를 정의할 수 있다.
//         * 해당 클래스에는 java.util.Properties || java.util.Map<String, Object> 를 인자로받는
//         * setDefaultProperties()가 있다.
//         * 한 번 정의하고 나중에 바뀌지 않는 설정을 이와 같이 적용하면된다.
//         */
//        SpringApplication application =
//                new SpringApplication(SpringBootInPracticeStudyApplication.class);
//        //SpringApplication.setDefaultProperties에 인자로 Peoperties로 지정하여 설정을 적용시킨다.
//        application.setDefaultProperties(properties);


        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBootInPracticeStudyApplication.class,args);

//        DbConfiguration dbConfiguration =
//                applicationContext.getBean(DbConfiguration.class);
//
//        log.info(dbConfiguration.toString());

        AppService appService =
                applicationContext.getBean(AppService.class);
        log.info(appService.getAppProperties().toString());




    }

}
