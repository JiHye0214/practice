package com.project.childprj.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostComment {
    private Long id;
    private Long postId;
    private User user;
    private UserImg userImg;
    private String content;
    private LocalDateTime createDate;
}
