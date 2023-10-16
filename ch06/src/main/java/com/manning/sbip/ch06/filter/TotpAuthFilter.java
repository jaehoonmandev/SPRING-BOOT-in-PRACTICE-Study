package com.manning.sbip.ch06.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.manning.sbip.ch06.service.TotpService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class TotpAuthFilter extends GenericFilterBean {

    private TotpService totpService;
    private static final String ON_SUCCESS_URL = "/index";
    private static final String ON_FAILURE_URL = "/totp-login-error";
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    public TotpAuthFilter(TotpService totpService) {
        this.totpService = totpService;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // SecurityContextHolder에서 인증 객체를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String code = request.getParameter("totp_code");

        // TOPT 인증 설정이 안되어이있다면.
        if(!requiresTotpAuthentication(authentication) || code == null) {
            //2차 인증을 활성화 하지 않았다면 바로 리턴.
            chain.doFilter(request, response);
            return;
        }
        //포함 되어 있다면. TOTP_AUTH_AUTHORITY 권한이 포함 되어있는지 확인.
        if(code != null && totpService.verifyCode(authentication.getName(), Integer.valueOf(code))) {
            Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            authorities.remove("TOTP_AUTH_AUTHORITY"); // OTP 가 맞다면 삭제한다. 이는 TOTP 활설화에만 쓰이는 값이다.
            authorities.add("ROLE_USER");// ROLE_USERS 권한부여
            //새로 부임한 권한은 UsernamePasswordAuthenticationToken으로 토큰을 생성하고
            authentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), buildAuthorities(authorities));
            //SecurityContextHolder 에 다시 저장한다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response, ON_SUCCESS_URL);
        }
        else {
            redirectStrategy.sendRedirect((HttpServletRequest) request, (HttpServletResponse) response, ON_FAILURE_URL);
        }
    }

    private boolean requiresTotpAuthentication(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        boolean hasTotpAutheority = authorities.contains("TOTP_AUTH_AUTHORITY");
        return hasTotpAutheority && authentication.isAuthenticated();
    }

    private List<GrantedAuthority> buildAuthorities(Collection<String> authorities) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);
        for(String authority : authorities) {
            authList.add(new SimpleGrantedAuthority(authority));
        }
        return authList;
    }
}
