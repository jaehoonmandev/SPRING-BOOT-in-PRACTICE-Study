package com.example.springbootinpracticestudy.Validation;

import com.example.springbootinpracticestudy.Validation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;

//비밀번호 유효성 검증 로직
//ConstraintValidator 인터페이스를 구현해야하는데
//첫 번째 인자는 커스텀 밸리데이터 로직을 적용하게 해주는 애노테이션.
//두 번째 인자는 커스텀 애노테이션을 적용해야하는 데이터 타입
public class PasswordRuleValidator implements ConstraintValidator<Password, String> {

    //Passay를 통한 Password 검증에 쓰일 값 설정
    private static final int MIN_COMPLEX_RULE = 2;
    private static final int MAX_REPETITIVE_CHAR =3;
    private static final int MIN_SPECIAL_CASE_CHARS = 1;
    private static final int MIN_UPPER_CASE_CHARS = 1;
    private static final int MIN_LOWER_CASE_CHARS = 1;
    private static final int MIN_DIGIT_CASE_CHARS = 1;


    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        List<Rule> passwordRules = new ArrayList<>();

        passwordRules.add(new LengthRule(8,30));

        CharacterCharacteristicsRule characterCharacteristicsRule =
                new CharacterCharacteristicsRule(
                        MIN_COMPLEX_RULE,
                        new CharacterRule(EnglishCharacterData.Special, MIN_SPECIAL_CASE_CHARS),
                        new CharacterRule(EnglishCharacterData.UpperCase, MIN_UPPER_CASE_CHARS),
                        new CharacterRule(EnglishCharacterData.LowerCase, MIN_LOWER_CASE_CHARS),
                        new CharacterRule(EnglishCharacterData.Digit, MIN_DIGIT_CASE_CHARS)
                        );
        passwordRules.add(characterCharacteristicsRule);

        passwordRules.add(new RepeatCharacterRegexRule(MAX_REPETITIVE_CHAR));

        PasswordValidator passwordValidator = new PasswordValidator(passwordRules);

        PasswordData passwordData = new PasswordData(password);

        RuleResult ruleResult = passwordValidator.validate(passwordData);

        return ruleResult.isValid();
    }
}
