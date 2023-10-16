package com.manning.sbip.ch06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.manning.sbip.ch06.dto.RecaptchaDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleRecaptchaService {
	//구글 reCaptcha 검증 서비스
	private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify"
			+ "?secret={secret}&remoteip={remoteip}&response={response}";
	private final RestTemplate restTemplate;
	
	@Value("${captcha.secret.key}") //application.properties 파일에서 값을 읽어온다.
	private String secretKey;

	public GoogleRecaptchaService(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RecaptchaDto verify(String ip, String recaptchaResponse) {
		Map<String, String> request = new HashMap<>();
		request.put("remoteip", ip);
		request.put("secret", secretKey);
		request.put("response", recaptchaResponse);

		//API 를 호출하여 사용자가 입력한 값을 검증하고 결과를 받아온다.
		ResponseEntity<Map> response = restTemplate.getForEntity(VERIFY_URL, Map.class, request);

		Map<String, Object> body = response.getBody();
		boolean success = (Boolean)body.get("success");
		RecaptchaDto recaptchaDto = new RecaptchaDto();
		recaptchaDto.setSuccess(success);
		if(!success) {
			recaptchaDto.setErrors((List)body.get("error-codes"));
		}
		return recaptchaDto;
	}
	
}
