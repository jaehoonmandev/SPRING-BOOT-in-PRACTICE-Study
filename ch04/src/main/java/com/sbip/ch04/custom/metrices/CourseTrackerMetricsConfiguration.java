package com.sbip.ch04.custom.metrices;

import com.sbip.ch04.service.CourseService;
import io.micrometer.core.instrument.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CourseTrackerMetricsConfiguration {
    //생성된 과정의 총 개수를 알기 위해 1씩 더해지는 커스텀 Counter bean 생성.
    //Counter는 종료 시 값이 초기화된다.
    @Bean
    public Counter createCourseCounter(MeterRegistry meterRegistry){
        return Counter.builder("api.courses.created.count") // Counter 의 이름
                .description("Total number of courses created") // 설명
                .register(meterRegistry);
    }

    //DB를 이용하여 값을 저장하며, Gauge 측정 지표를 통해서 값을 확인한다.
    @Bean
    public Gauge createCourseGauge(MeterRegistry meterRegistry, CourseService courseService){
        return Gauge.builder("api.courses.create.gauge", courseService::count)
                .description("Total courses created")
                .register(meterRegistry);
    }

    @Bean
    public Timer createCoursesTimer(MeterRegistry meterRegistry) {
        return Timer.builder("api.courses.creation.time")
                .description("Course creation time")
                /*.sla(Duration.ofMillis(10))
                .minimumExpectedValue(Duration.ofMillis((1)))
                .maximumExpectedValue(Duration.ofMillis(10))
                .publishPercentiles(0.5, 0.95)
                .publishPercentileHistogram()*/
                .register(meterRegistry);
    }

    @Bean
    public DistributionSummary createDistributionSummary(MeterRegistry meterRegistry) {
        return DistributionSummary.builder("api.courses.rating.distribution.summary")
                .description("Rating distribution summary")
                .register(meterRegistry);
    }
}
