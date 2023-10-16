package com.manning.sbip.ch06.service;

import com.manning.sbip.ch06.dto.UserDto;
import com.manning.sbip.ch06.model.User;

public interface UserService {
    User createUser(UserDto userDto); // 유저 생성
    User save(User User);// 저장
    User findByUsername(String username); // username 기준 조회
}
