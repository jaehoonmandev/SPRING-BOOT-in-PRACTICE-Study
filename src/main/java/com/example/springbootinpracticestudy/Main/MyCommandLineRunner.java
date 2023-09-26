package com.example.springbootinpracticestudy.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1) // CommandLineRunner의 구현체가 여러개 있을 때 컴포넌트의 순서를 정한다.
@Component
public class MyCommandLineRunner implements CommandLineRunner {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(String... args) throws Exception {
        logger.info("MyCommandLineRunner executed as a Spring Component");
    }
}
