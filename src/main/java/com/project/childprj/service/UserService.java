package com.project.childprj.service;

import com.project.childprj.domain.User;
import com.project.childprj.domain.UserAuthority;
import com.project.childprj.domain.UserImg;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface UserService {

    User findByLogId(String loginId);

    UserAuthority selectUserAuth(Long userId);

    boolean isExistId(String loginId);

    boolean isExistNn(String nickname);

    boolean isExistEm(String email);

    int signUp(User user);

    boolean findIdPwByEmail(String name, String email);

    User userIdIs(String email);

    boolean findPwById(String name, String loginId);

    int modifyNickname(User user);

    int modifyPassword(User user);

    int dropUser(User user);

    UserImg findUserImg (Long userId);

    boolean insertImg (Map<String, MultipartFile> file);

}
