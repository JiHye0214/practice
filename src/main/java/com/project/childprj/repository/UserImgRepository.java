package com.project.childprj.repository;

import com.project.childprj.domain.UserImg;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImgRepository {

    UserImg findUserImg(Long userId);

    int imgInsert(UserImg userImg);

    int imgDelete(Long userId);

}
