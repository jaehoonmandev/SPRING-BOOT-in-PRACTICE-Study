package com.example.springbootinpracticestudy.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//@Password 애노테이션이 적용되는 범위를 메서드와 필드로 지정
@Target({ElementType.METHOD, ElementType.FIELD})
//런타임 동안 효력 발휘
@Retention(RetentionPolicy.RUNTIME)
// Bean 밸리데이션 제약 사항을 포함하는 애너테이션임을 의미, validatedBy를 통하여 제약 사항이 구현된 클래스 지정.
@Constraint(validatedBy = PasswordRuleValidator.class)
//커스텀 @Password 애노테이션 설정.
public @interface Password {
    String message() default "비밀번호 규율을 지키세요";
    Class<?>[] groups() default{}; // 그룹별로 밸리데이션 지정
    Class<? extends Payload>[] payload() default{};
}
