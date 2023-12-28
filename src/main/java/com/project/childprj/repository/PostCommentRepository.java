package com.project.childprj.repository;

import com.project.childprj.domain.PostComment;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostCommentRepository {

    List<PostComment> findCommentById(Long postId);

    PostComment findOneCmtById(Long commentId);

    int cmtWrite(Long userId, Long postId, String content);

    int cmtRemove(Long commentId);
}
