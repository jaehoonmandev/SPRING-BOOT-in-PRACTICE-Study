package com.manning.sbip.ch06.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Service;

import com.manning.sbip.ch06.service.LoginAttemptService;

//스프링 시큐리티에서 로그인(인증) 실패 시 호출되는 AuthenticationFailureBadCredentialsEvent 이벤트 리스너
//@Service
public class AuthenticationFailureEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

   // @Autowired
    private LoginAttemptService loginAttemptService;

    //해당 이벤트가 호출 되었다는건 로그인에 실패했다는 의미로 횟수를 증가시킨다.
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent) {
        String username = (String) authenticationFailureBadCredentialsEvent.getAuthentication().getPrincipal();
        loginAttemptService.loginFailed(username);
    }
}