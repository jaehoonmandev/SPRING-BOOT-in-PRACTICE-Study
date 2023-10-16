package com.manning.sbip.ch06.service;

import com.manning.sbip.ch06.dto.UserDto;
import com.manning.sbip.ch06.model.User;
import com.manning.sbip.ch06.repository.UserRepository;
import com.manning.sbip.ch06.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//UserRepository 에서 선언한 커스텀 메서드를 구현해준다.
//UserService에서 선언한 findByUsername는 JPA 구문을 사용하여 자동으로 쿼리가 작성된다.
@Service
public class DefaultUserService implements UserService {

    //CrudRepository 가 구현해주는 기본 기능을 커스텀 하기 위함.
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    //사용자 정보 입력 후 UserDto로 맵핑된 정보를 하나씩 model로 지정하여 Repository로 넘겨주며 최종적으로 DB에 저장하게 된다.
    public User createUser(UserDto userDto) {
       User User = new User();
       User.setFirstName(userDto.getFirstName());
       User.setLastName(userDto.getLastName());
       User.setEmail(userDto.getEmail());
       User.setUsername(userDto.getUsername());
       User.setPassword(passwordEncoder.encode(userDto.getPassword()));

       return userRepository.save(User);
    }

    @Override
    public User save(User User) {
        return userRepository.save(User);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
