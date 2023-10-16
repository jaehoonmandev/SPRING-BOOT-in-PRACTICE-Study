package com.manning.sbip.ch06.controller;

import com.manning.sbip.ch06.dto.RecaptchaDto;
import com.manning.sbip.ch06.dto.UserDto;
import com.manning.sbip.ch06.event.UserRegistrationEvent;
import com.manning.sbip.ch06.model.User;
import com.manning.sbip.ch06.service.UserService;

import com.manning.sbip.ch06.service.GoogleRecaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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
    // 사용자 등록이 감지하는 이벤트를 등록하기 위한 퍼블리셔
//    @Autowired
//    private ApplicationEventPublisher eventPublisher;
//
//    @Value("${app.email.verification:N}")
//    private String emailVerification;
//
//    // 리켑챠 기능 이용
//    @Autowired
//    private GoogleRecaptchaService captchaService;



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
            //BindingResult result,
            HttpServletRequest httpServletRequest,
            BindingResult result) {
        //실패 시 실패 했던 항목들의 에러 메시지를 함께 보내준다.
        if(result.hasErrors()) {
            return "add-user";
        }
//
//        //사용자가 리캡차 체크박스를 체크하고 요청을 보냈다면 자동 생성된 파라미터 값을 넘겨받는다.
//        String response = httpServletRequest.getParameter("g-recaptcha-response");
//        if(response == null) {
//            return "add-user";
//        }
//
//        // 리켑차 인증을 위한 API 호출
//        // 사용자의 IP 주소와 g-recaptcha-response 값을 넘겨 API를 통해 성공 여부를 전달 받는다.
//        String ip = httpServletRequest.getRemoteAddr();
//        RecaptchaDto recaptchaDto = captchaService.verify(ip, response);
//
//        if(!recaptchaDto.isSuccess()) {
//            return "redirect:adduser?incorrectCaptcha";
//        }
//
//        User User = userService.createUser(userDto);
//        if("Y".equalsIgnoreCase(emailVerification)) {
//            //문제 없을 시 저장.
//            //userService.createUser(userDto);
//            // 검증 메일 이벤트 리스너를 퍼블리싱한다.
//              eventPublisher.publishEvent(new UserRegistrationEvent(User));
//              return "redirect:adduser?validate";
//        }
        userService.createUser(userDto);

        return "redirect:adduser?success";
    }
}
