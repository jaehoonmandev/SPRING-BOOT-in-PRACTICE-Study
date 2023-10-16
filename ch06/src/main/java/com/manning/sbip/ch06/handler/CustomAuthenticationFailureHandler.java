package com.manning.sbip.ch06.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    // 로그인(인증) 실패 시 핸들링하기 위한 이벤트 핸들러.
    private DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

//        if(exception instanceof DisabledException) { //아직 인증하지 않은 계정
//            defaultRedirectStrategy.sendRedirect(request, response, "/login-disabled");
//            return;
//        }
        if(exception.getCause() instanceof LockedException) {
            defaultRedirectStrategy.sendRedirect(request, response, "/login-locked");
            return;
        }
        defaultRedirectStrategy.sendRedirect(request, response, "/login-error");
    }

}
