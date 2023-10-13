package com.manning.sbip.ch06.controller;

import com.manning.sbip.ch06.dto.UserDto;
import com.manning.sbip.ch06.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

//사용자 정보 등록 컨트롤러
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    //단순 호출 시에는 사용자 등록 페이지를 리턴해준다.
    @GetMapping("/adduser")
    public String register(Model model) {
        //UserDto 에 선언된 필드들은 View 딴에서 바인딩 오브젝트로 처리된다.(Thymeleaf)
        model.addAttribute("user", new UserDto());
        return "add-user";
    }

    //form 을 이용하여 POST 요청을 한 데이터들은 객체화 돼서 넘어온다.
    @PostMapping("/adduser")
    public String register(
            //@Valid을 통해 DTO에 지정한 제약사항 체크,
            //form 에서 user라는 이름으로 객체를 넘겼으니 동일한 이름으로 @ModelAttribute를 설정하여 UserDto를 맵핑
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result) {
        //실패 시 실패 했던 항목들의 에러 메시지를 함께 보내준다.
        if(result.hasErrors()) {
            return "add-user";
        }
        //문제 없을 시 저장.
        userService.createUser(userDto);
        //저장 했던 정보들과 함께 전달.
        return "redirect:adduser?success";
    }
}
