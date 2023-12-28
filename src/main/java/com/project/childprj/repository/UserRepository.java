package com.project.childprj.repository;

import com.project.childprj.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User findUserById(Long id);

    int newUser(User user);

    User findUserByLogId(String loginId);

    User findUserByNickname(String nickname);

    User findUserByEmail(String email);

    User findIdPwByEmail(String name, String email);

    User findPwById(String name, String loginId);

    int fixNickname(User user);

    int fixPassword(User user);

    int dropUser(User user);

}
