package com.project.childprj.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private LocalDateTime createDate;

    private UserAuthority authority;
}
