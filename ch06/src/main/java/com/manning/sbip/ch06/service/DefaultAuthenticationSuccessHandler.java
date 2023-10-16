package com.manning.sbip.ch06.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    //로그인 성공 후 해당 사용자 인증 체계에서 TOTP_AUTH_AUTHORITY 권한 있는지 확인
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //있다면 OTP 2차인증 페이지.
        if(isTotpAuthRequired(authentication)) {
            redirectStrategy.sendRedirect(request, response, "/totp-login");
        }
        // 없다면 사용자가 2차인증 인증을 설정할 수 있는 페이지로.
        else {
            redirectStrategy.sendRedirect(request, response, "/account");
        }
    }

    private boolean isTotpAuthRequired(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        return authorities.contains("TOTP_AUTH_AUTHORITY");
    }

}
