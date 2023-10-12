package com.manning.sbip.ch05.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    //AccessDeniedHandler 커스텀
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //AccessDeniedHandler로 권한 밖의 기능을 요청했다면 /accessDenied 로 redirect
        response.sendRedirect(request.getContextPath() + "/accessDenied");
    }
}
