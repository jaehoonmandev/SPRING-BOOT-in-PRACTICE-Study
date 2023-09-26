package com.example.springbootinpracticestudy.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:dbConfig.properties")
public class DbConfiguration {
    /**
     * @PropertuSource 를 통하여 java 클래스 내부에서 classpath(현재는 src/main/resources)에 있는 설정 파일을 읽어올 수 있다.(여러개 지정가능)
     * Enviroment 인스턴스는 @PropertySource로 읽어온 설정을 @Autowired하여 값을 일어온다.
     */
    @Autowired
    private Environment env;

    @Override
    public String toString(){
        return "Username: " + env.getProperty("user") +
                ", Password: " + env.getProperty("password");
    }

}
