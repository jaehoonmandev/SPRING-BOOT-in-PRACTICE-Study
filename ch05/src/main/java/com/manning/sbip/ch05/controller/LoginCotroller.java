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
}
