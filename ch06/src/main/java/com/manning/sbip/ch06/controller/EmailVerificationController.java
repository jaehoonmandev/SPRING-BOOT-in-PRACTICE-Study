package com.manning.sbip.ch06.controller;

import com.manning.sbip.ch06.model.User;
import com.manning.sbip.ch06.service.EmailVerificationService;
import com.manning.sbip.ch06.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
public class EmailVerificationController {

    //이메일 인증 링크 클릭 시 인증을 진행할 컨트롤러.

    @Autowired
    private EmailVerificationService verificationService;
    @Autowired
    private UserService userService;

//    @GetMapping("/verify/email")
//    public String verifyEmail(@RequestParam String id) {// URI에 포함되어 오는 파라미터 값을 id로 맵핑.
//        byte[] actualId = Base64.getDecoder().decode(id.getBytes());//인코딩된 값을 디코딩.
//        String username = verificationService.getUsernameForVerificationId(new String(actualId));
//        //일치하는 유저가 있다면
//        if(username != null) {
//            User user = userService.findByUsername(username); //해당 아이디를 가진 유저의 정보를 불러와
//            user.setVerified(true); // verified를 true로 설정.
//            userService.save(user); // 저장
//            return "redirect:/login-verified"; // 인증 완료 페이지로 리다이렉트
//        }
//        return "redirect:/login-error"; // 없다면 에러 페이지.
//    }
}
