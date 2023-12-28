package com.project.childprj.service;

import com.project.childprj.domain.PostComment;
import java.util.List;

public interface PostCommentService {

    List<PostComment> cmtList(Long postId);

    int cmtWrite(Long userId, Long postId, String content);

    int cmtRemove(Long commentId);
}
