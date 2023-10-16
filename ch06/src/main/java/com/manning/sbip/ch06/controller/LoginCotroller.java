package com.manning.sbip.ch06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    //로그인 실패 시
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    //인증된
    @GetMapping("/login-verified")
    public String loginVerified(Model model) {
        model.addAttribute("loginVerified", true);
        return "login";
    }

    //인증이 되지 않은.
    @GetMapping("/login-disabled")
    public String loginDisabled(Model model) {
        model.addAttribute("loginDisabled", true);
        return "login";
    }

    //로그인 실패 횟수 초과 시
    @GetMapping("/login-locked")
    public String loginLocked(Model model) {
        model.addAttribute("loginLocked", true);
        return "login";
    }

}
