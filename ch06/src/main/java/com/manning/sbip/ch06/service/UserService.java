package com.manning.sbip.ch06.service;

import com.manning.sbip.ch06.dto.UserDto;
import com.manning.sbip.ch06.model.ApplicationUser;

public interface UserService {
    ApplicationUser createUser(UserDto userDto); // 유저 생성
    ApplicationUser findByUsername(String username); // username 기준 조회
}
