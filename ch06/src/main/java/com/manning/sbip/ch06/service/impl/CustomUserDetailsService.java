package com.manning.sbip.ch06.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manning.sbip.ch06.model.CustomUser;
import com.manning.sbip.ch06.model.User;
import com.manning.sbip.ch06.repository.UserRepository;

import java.util.Arrays;

//스프링 시큐리티 사용자 관리 기능을 연동하기 위한 UserDetailsService 구현
//기본 스프링 시큐리티에서 인식하는 테이블과 다른 명을 사용해주기에 커스텀을 진행해야한다.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		SimpleGrantedAuthority simpleGrantedAuthority = null;
		if(user.isTotpEnabled()) {
			//2차 인증 활성화가 된 유저에게 TOTP_AUTH_AUTHORITY 권한 부여
			simpleGrantedAuthority = new SimpleGrantedAuthority("TOTP_AUTH_AUTHORITY");
		}
		else {
			//2차 인증 활성화 하지 않은 사용자는 ROLE_USER 권한 부여.
			simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
		}
		CustomUser customUser = new CustomUser(
				user.getUsername(),
				user.getPassword(),
				true,
				true,
				true,
				true,
				Arrays.asList(simpleGrantedAuthority));
		customUser.setTotpEnabled(user.isTotpEnabled());
		return customUser;

	}
}
