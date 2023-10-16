package com.manning.sbip.ch06.service.impl;

import com.manning.sbip.ch06.model.ApplicationUser;
import com.manning.sbip.ch06.service.LoginAttemptService;
import com.manning.sbip.ch06.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//스프링 시큐리티 사용자 관리 기능을 연동하기 위한 UserDetailsService 구현
//기본 스프링 시큐리티에서 인식하는 테이블과 다른 명을 사용해주기에 커스텀을 진행해야한다.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

	@Autowired
	private LoginAttemptService loginAttemptService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//블락 당한 유저라면.
		if(loginAttemptService.isBlocked(username)) {
			throw new LockedException("User Account is Locked");
		}

		ApplicationUser applicationUser = userService.findByUsername(username);
		if(applicationUser == null) {
			throw new UsernameNotFoundException("User with username "+username+" does not exists");
		}

		return User.withUsername(username).password(applicationUser.getPassword()).roles("USER").disabled(!applicationUser.isVerified()).build();

	}
}
