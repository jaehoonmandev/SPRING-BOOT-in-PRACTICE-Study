package com.sbip.ch04;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Ch04Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch04Application.class, args);
    }

    //MeterRegistry를 사용하면 자동 구성으로 여러 개의 레지스트리 구현체를 추가할 수 있다.
    //MeterRegistryCustomizer를 사용해 레지스트리 커스터마이징.
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
                .commonTags("applicationName", "course-tracker");// 태그는 쿼리스트링 식발자로 사용된다.
    }
}
