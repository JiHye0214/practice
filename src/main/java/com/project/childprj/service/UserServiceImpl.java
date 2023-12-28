package com.project.childprj.service;

import com.project.childprj.domain.User;
import com.project.childprj.domain.UserAuthority;
import com.project.childprj.domain.UserImg;
import com.project.childprj.repository.UserAuthorityRepository;
import com.project.childprj.repository.UserImgRepository;
import com.project.childprj.repository.UserRepository;
import com.project.childprj.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.upload.path}")
    private String uploadDir;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserImgRepository userImgRepository;
    UserRepository userRepository;
    UserAuthorityRepository userAuthorityRepository;

    @Autowired
    public UserServiceImpl(SqlSession sqlSession){
        userImgRepository = sqlSession.getMapper(UserImgRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        userAuthorityRepository = sqlSession.getMapper(UserAuthorityRepository.class);
        System.out.println("UserRepository is created()");
    }

    @Override
    public User findByLogId(String loginId) {
        return userRepository.findUserByLogId(loginId);
    }

    @Override
    public UserAuthority selectUserAuth(Long userId) {
        User user = userRepository.findUserById(userId);
        return userAuthorityRepository.findByUser(user.getId());
    }

    @Override
    public boolean isExistId(String loginId) {
        User user = userRepository.findUserByLogId(loginId);
        return (user != null);
    }

    @Override
    public boolean isExistNn(String nickname) {
        User user = userRepository.findUserByNickname(nickname);
        return (user != null);
    }

    @Override
    public boolean isExistEm(String email) {
        User user = userRepository.findUserByEmail(email);
        return (user != null);
    }

    @Override
    public boolean findIdPwByEmail(String name, String email) {
        User user = userRepository.findIdPwByEmail(name, email);
        return (user != null);
    }

    @Override
    public User userIdIs(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean findPwById(String name, String loginId) {
        User user = userRepository.findPwById(name, loginId);
        return (user != null);
    }

    @Override
    public int signUp(User user) {
        // password encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // sign up
        userRepository.newUser(user);
        // authority setting
        userAuthorityRepository.addAuthority(user.getId(), 1L);

        UserImg userImg = UserImg.builder()
                        .userId(user.getId())
                        .sourceName("default.jpg")
                        .fileName("default.jpg")
                        .build();

        userImgRepository.imgInsert(userImg);
        return 1;
    }

    @Override
    @Transactional
    public int modifyNickname(@RequestBody  User user) {
        int change = userRepository.fixNickname(user);
        return change;
    }

    @Override
    @Transactional
    public int modifyPassword(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int change = userRepository.fixPassword(user);
        return change;
    }

    @Override
    public int dropUser(User user) {
        return userRepository.dropUser(user);
    }

    @Override
    public UserImg findUserImg(Long userId) {
        return userImgRepository.findUserImg(userId);
    }

    @Override
    public boolean insertImg(Map<String, MultipartFile> files) {

        Long userId = U.getLoggedUser().getId();
        changeImg(files, userId);

        return (files != null);
    }

    private void changeImg(Map<String, MultipartFile> files, Long userId) {

        if(userImgRepository.findUserImg(userId) != null){
            int result = userImgRepository.imgDelete(userId);
        }

        if(files == null) return;


        for(Map.Entry<String, MultipartFile> e : files.entrySet()){

            if(!e.getKey().startsWith("user")) continue;

            UserImg profile = upload(e.getValue());

            if(profile != null) {
                profile.setUserId(userId);
                userImgRepository.imgInsert(profile);
            }
        }

    }

    private UserImg upload(MultipartFile multipartFile) {

        UserImg userImg = null;
        String sourceName = null;
        String fileName = null;

        String originalFilename = multipartFile.getOriginalFilename();

        if(originalFilename.isEmpty()) {
            sourceName = "default.jpg";

            fileName = sourceName;

        } else {
            sourceName = StringUtils.cleanPath(originalFilename);

            fileName = sourceName;

            File file = new File(uploadDir, fileName);

            if(file.exists()){

                int pos = fileName.lastIndexOf(".");
                if(pos > -1){
                    String name = fileName.substring(0, pos);
                    String ext = fileName.substring(pos + 1);

                    fileName = name + "_" + System.currentTimeMillis() + "." + ext;
                } else {
                    fileName += "_" + System.currentTimeMillis();
                }
            }

            Path copyOfLocation = Paths.get(new File(uploadDir, fileName).getAbsolutePath());

            try {
                Files.copy(
                        multipartFile.getInputStream(),
                        copyOfLocation,
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        userImg = UserImg.builder()
                .sourceName(sourceName)
                .fileName(fileName)
                .build();

        return userImg;
    }

}





