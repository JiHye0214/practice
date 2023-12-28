package com.project.childprj.repository;

import com.project.childprj.domain.UserAuthority;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorityRepository {

    UserAuthority findAuthName(String authName);

    int addAuthority(Long userId, Long authorityId);

    UserAuthority findByUser(Long userId);
}
