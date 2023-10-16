package com.manning.sbip.ch06.dto;

import java.util.List;

//reCpaptcha 인증시 넘어 오는 값들
public class RecaptchaDto {

	private boolean success;
	private List<String> errors;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
