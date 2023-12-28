package com.project.childprj.domain;

import com.project.childprj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    UserService userService;

    public UserValidator(UserService userService){
        this.userService = userService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        boolean result = User.class.isAssignableFrom(clazz);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String loginId = user.getLoginId();
        String nickname = user.getNickname();
        String email = user.getEmail();

        if(userService.isExistId(loginId)) {
            errors.rejectValue("loginId", "이미 존재하는 아이디입니다");
        }
        if(userService.isExistNn(nickname)) {
            errors.rejectValue("nickname", "이미 존재하는 닉네임입니다");
        }
        if(userService.isExistEm(email)) {
            errors.rejectValue("email", "이미 존재하는 이메일입니다");
        }
    }
}