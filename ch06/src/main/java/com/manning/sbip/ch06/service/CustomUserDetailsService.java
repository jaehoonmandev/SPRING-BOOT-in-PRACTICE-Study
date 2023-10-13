package com.manning.sbip.ch06.service;

import com.manning.sbip.ch06.model.ApplicationUser;
import com.manning.sbip.ch06.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    //UserDetailsService 인터페이스에는 loadUserByUsername(...) 메서드만 있다. 이를 구현해준다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //ApplicationUserRepository에 정의한 findByUsername로 조회.
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

        if(applicationUser == null) {
            //없을 때 에러 도출.
            throw new UsernameNotFoundException("No user with "+username+" exists in the system");
        }
        return User.builder()
                .username(applicationUser.getUsername())
                .password(applicationUser.getPassword())
                .disabled(!applicationUser.isVerified())
                .accountExpired(applicationUser.isAccountCredentialsExpired()) // 비밀번호 만료 설정으로 변경을 강제할 수 있다.
                .accountLocked(applicationUser.isLocked()) // 잠겨있는지
                // 스프링 시큐리티 인증 사용을 위해서는 역할이나 권한을 반드시 설정해주어야한다.
                //.roles("USER") // 역할 설정
                .authorities("ROLE_USER") // 권한설정
                .build();
    }
}
