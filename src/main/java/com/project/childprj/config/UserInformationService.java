package com.project.childprj.config;

import com.project.childprj.domain.User;
import com.project.childprj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB 조회하기
        User user = userService.findByLogId(username);

        if(user != null) {
            UserInformation userInformation = new UserInformation(user);
            userInformation.setUserService(userService);
            return userInformation;
        }

        throw new UsernameNotFoundException(username);
    }
}
