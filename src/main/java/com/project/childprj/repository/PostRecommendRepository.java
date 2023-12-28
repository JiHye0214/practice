package com.project.childprj.repository;

import com.project.childprj.domain.PostRecommend;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRecommendRepository {

    PostRecommend clickCheck(Long userId, Long postId);

    int recommend(Long userId, Long postId);

    int opposite(Long userId, Long postId);
}
