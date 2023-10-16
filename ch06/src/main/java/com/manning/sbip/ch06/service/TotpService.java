package com.manning.sbip.ch06.service;

import com.manning.sbip.ch06.exception.InvalidVerificationCode;
import com.manning.sbip.ch06.model.TotpDetails;
import com.manning.sbip.ch06.model.User;
import com.manning.sbip.ch06.repository.TotpRepository;
import com.manning.sbip.ch06.repository.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class TotpService {

    private final GoogleAuthenticator googleAuth = new GoogleAuthenticator();
    private final TotpRepository totpRepository;
    private final UserRepository userRepository;
    private static final String ISSUER = "CourseTracker"; // 등록되는 인증 이름

    public TotpService(TotpRepository totpRepository, UserRepository userRepository) {
        this.totpRepository = totpRepository;
        this.userRepository = userRepository;
    }

    // 사용자 username에 맞는 QR URL을 생성.
    @Transactional
    public String generateAuthenticationQrUrl(String username){
        GoogleAuthenticatorKey authenticationKey = googleAuth.createCredentials();
        String secret = authenticationKey.getKey();
        totpRepository.deleteByUsername(username);
        totpRepository.save(new TotpDetails(username, secret));
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(ISSUER, username, authenticationKey);
    }

    public boolean isTotpEnabled(String userName) {
        return userRepository.findByUsername(userName).isTotpEnabled();
    }

    // 입력된 OTP가 올바른 값인지 검증하고 2단계 인증을 활성화
    public void enableTotpForUser(String username, int code){
        if(!verifyCode(username, code)) {
            throw new InvalidVerificationCode("Invalid verification code");
        }

        User user = userRepository.findByUsername(username);
        user.setTotpEnabled(true);
        userRepository.save(user);
    }

    public boolean verifyCode(String userName, int verificationCode) {
        TotpDetails totpDetails = totpRepository.findByUsername(userName);
        return googleAuth.authorize(totpDetails.getSecret(), verificationCode);
    }
}
