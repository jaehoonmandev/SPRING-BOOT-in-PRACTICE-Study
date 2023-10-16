package com.manning.sbip.ch06.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.manning.sbip.ch06.service.LoginAttemptService;

// 스프링 시큐리티 로그인 성공 시 호출되는 AuthenticationSuccessEvent 의 이벤트 리스너
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
	
    @Autowired
    private LoginAttemptService loginAttemptService;

    //이벤트 성공 시 해당 유저의 실패 횟수를 무효화한다.
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
    	 User user= (User) authenticationSuccessEvent.getAuthentication().getPrincipal(); // 사용자 인증 정보 추출.
         loginAttemptService.loginSuccess(user.getUsername());
    }
}
