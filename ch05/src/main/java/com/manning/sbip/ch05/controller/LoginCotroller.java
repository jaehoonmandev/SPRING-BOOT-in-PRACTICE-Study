package com.manning.sbip.ch05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginCotroller {

    //login.html 맵핑
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    //권한 외 기능을 실행할 떄 Override한 AccessDenied로 부터 /accessDenied redirect 전달
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
