package com.manning.sbip.ch06.service;

import com.manning.sbip.ch06.model.EmailVerification;
import com.manning.sbip.ch06.repository.EmailVerficationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailVerificationService {
    private final EmailVerficationRepository repository;

    @Autowired
    public EmailVerificationService(EmailVerficationRepository repository){
        this.repository = repository;
    }

    //사용자가 등록될 때 EmailVerification 또한 등록한다.
    public String generateVerification(String username) {
        //기존에 인증 테이블에 등록 안되었다면 저장.
        if (!repository.existsByUsername(username)) {
            EmailVerification verification = new EmailVerification(username);
            verification = repository.save(verification);
            return verification.getVerificationId();
        }
        return getVerificationIdByUsername(username);
    }

    //유저 이름으로 인증 아이디를 반환.
    public String getVerificationIdByUsername(String username) {
        EmailVerification verification = repository.findByUsername(username);
        if(verification != null) {
            return verification.getVerificationId();
        }
        return null;
    }

    //아이디에 해당하는 유저 아이디 반환
    public String getUsernameForVerificationId(String verificationId) {
        Optional<EmailVerification> verification = repository.findById(verificationId);
        if(verification.isPresent()) {
            return verification.get().getUsername();
        }
        return null;
    }

}
