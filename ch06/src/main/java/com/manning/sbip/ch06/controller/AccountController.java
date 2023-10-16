package com.manning.sbip.ch06.controller;

import com.manning.sbip.ch06.exception.InvalidVerificationCode;
import com.manning.sbip.ch06.model.CustomUser;
import com.manning.sbip.ch06.model.TotpCode;
import com.manning.sbip.ch06.service.TotpService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class AccountController {

	private final TotpService totpService; //tOTP를 검증하고 생성하는.

	//totpEnabled, configureTotp 를 설정한 후 2단계 인증을 활성화 할 수 있는 페이지로 이동
	@GetMapping("/account")
	public String getAccount(Model model, @AuthenticationPrincipal CustomUser customUser) {
		if (customUser != null && !customUser.isTotpEnabled()) {
			model.addAttribute("totpEnabled", customUser.isTotpEnabled());
			model.addAttribute("configureTotp", true);
		} else {
			model.addAttribute("totpEnabled", true);
		}
		return "account";
	}

	//2단계 인증을 설정할 수 있다.
	@GetMapping("/setup-totp")
	public String getGoogleAuthenticatorQrUrl(Model model, @AuthenticationPrincipal CustomUser customUser) {
		String username = customUser.getUsername();
		boolean isTotp = customUser.isTotpEnabled();
		//tOTP 설정이 되어있지 않는다면
		if (!isTotp) {
			//QR 코드를 생성하여 사용자가 구글 오센티케이터 앱을 이용해 등록할 수 있도록.
			model.addAttribute("qrUrl", totpService.generateAuthenticationQrUrl(username));
			model.addAttribute("code", new TotpCode());
			return "account";
		}
		model.addAttribute("totpEnabled", true);
		return "account";
	}

	//TOTP를 활성화하는 엔드포인트.
	@PostMapping("/confirm-totp")
	public String confirmGoogleAuthenticatorSetup(Model model, @AuthenticationPrincipal CustomUser customUser,
			TotpCode totpCode) {
		boolean isTotp = customUser.isTotpEnabled();
		if (!isTotp) {
			try {
				totpService.enableTotpForUser(customUser.getUsername(), Integer.valueOf(totpCode.getCode()));
			} catch (InvalidVerificationCode ex) {
				model.addAttribute("totpEnabled", customUser.isTotpEnabled());
				model.addAttribute("confirmError", true);
				model.addAttribute("configureTotp", false);
				model.addAttribute("code", new TotpCode());
				return "account";
			}

			model.addAttribute("totpEnabled", true);
		}
		customUser.setTotpEnabled(true);
		return "redirect:/logout";
	}

	@ExceptionHandler(InvalidVerificationCode.class)
	public String handleInvalidTOTPVerificationCode(InvalidVerificationCode ex, Model model,
			@AuthenticationPrincipal CustomUser user) {
		boolean userHasTotpEnabled = user.isTotpEnabled();
		model.addAttribute("totpEnabled", userHasTotpEnabled);
		model.addAttribute("confirmError", true);
		model.addAttribute("code", new TotpCode());
		return "account";
	}
}
