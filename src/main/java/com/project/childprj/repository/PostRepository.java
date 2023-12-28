package com.project.childprj.repository;

import com.project.childprj.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository {
    List<Post> selectFromCntOrderByDate(int from, int cnts, String sq);

    List<Post> selectFromCntOrderByRcmCnt(int from, int cnts, String sq);

    int selectCountAll(String sq);

    int insert(Post post);

    Post findPostById(Long id);

    int incViewCnt(Long id);

    int incRecomCnt(Long id);

    int decRecomCnt(Long id);

    int detailDelete(Long id);

    int update(Post post);

    List<Post> selectFive();

}
