package com.sbip.ch04.health.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component // Bean으로 등록
//HealthIndicator 구현으로 어떤 애플리케이션 컴포넌트의 상태를 알려주는 역할을 한다.
//DogsApiHealthIndicator의 클래스 이름에서 HealthIndicator를 제외한 이름으로 항목 이름이 정해진다.
public class DogsApiHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            ParameterizedTypeReference<Map<String, String>> reference
                    = new ParameterizedTypeReference<Map<String, String>>() {};

            //Spring의 RestTemplate를 사용하여 API 호출
            ResponseEntity<Map<String, String>> result
                    = new RestTemplate().exchange("https://dog.ceo/api/breeds/image/random", HttpMethod.GET, null, reference);

            //코드 값을 기반으로 결과 출력.
            if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
                return Health.up().withDetails(result.getBody()).build();
            }
            else {
                return Health.down().withDetail("status", result.getStatusCode()).build();
            }
        }
        catch(RestClientException ex) {
            //호출 시 오류가 난다면.
            return Health.down().withException(ex).build();
        }
    }
}
