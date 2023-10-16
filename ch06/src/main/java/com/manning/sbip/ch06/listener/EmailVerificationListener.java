package com.manning.sbip.ch06.listener;

import com.manning.sbip.ch06.event.UserRegistrationEvent;
import com.manning.sbip.ch06.model.ApplicationUser;
import com.manning.sbip.ch06.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EmailVerificationListener implements ApplicationListener<UserRegistrationEvent> {
    private final JavaMailSender mailSender; // 이메일 발송 기능 주입.
    private final EmailVerificationService verificationService; // 등록 시 생성된 ID값을 username을 통해 조회할 수 있도록 주입.

    @Autowired
    public EmailVerificationListener(JavaMailSender mailSender, EmailVerificationService verificationService) {
        this.mailSender = mailSender;
        this.verificationService = verificationService;
    }

    //UserRegistrationEvent 발생 시 이메일 내용구성하고 발송하는 이벤트 핸들러 구성.
    public void onApplicationEvent(UserRegistrationEvent event) {
        ApplicationUser user = event.getUser();
        String username = user.getUsername();
        String verificationId = verificationService.generateVerification(username);
        String email = event.getUser().getEmail();

        SimpleMailMessage message = new SimpleMailMessage(); // 메일 구성
        message.setSubject("Course Tracker Account Verification"); // 제목
        message.setText(getText(user, verificationId));//이메일 내용 구성
        message.setTo(email);//보낼 주소
        mailSender.send(message);
    }

    private String getText(ApplicationUser user, String verificationId) {
        //verificationId 를 Base64로 인코딩한다.
        String encodedVerificationId = new String(Base64.getEncoder().encode(verificationId.getBytes()));
        //메일 내용 구성
        StringBuffer buffer = new StringBuffer();

        //유저 아이디 포함 소개문
        buffer.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",").append(System.lineSeparator()).append(System.lineSeparator());
        buffer.append("Your account has been successfully created in the Course Tracker application. ");

        //인증 로직을 거칠 링크 포함 전송.
        buffer.append("Activate your account by clicking the following link: http://localhost:8080/verify/email?id=").append(encodedVerificationId);

        buffer.append(System.lineSeparator()).append(System.lineSeparator());
        buffer.append("Regards,").append(System.lineSeparator()).append("Course Tracker Team");
        return buffer.toString();
    }

}
