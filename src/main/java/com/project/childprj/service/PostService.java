package com.project.childprj.service;

import com.project.childprj.domain.Post;
import org.springframework.ui.Model;
import java.util.List;

public interface PostService {
    List<Post> list(Integer page, String sq, String postOrderWay, Model model);

    int write(Post post);

    Post postDetail(Long id);

    void incViewCnt(Long id);

    int detailDelete(Long id);

    boolean clickCheck(Long userId, Long postId);

    int recommend(Long userId, Long postId);

    int opposite(Long userId, Long postId);

    int update(Post post);

    List<Post> selectFive();
}
