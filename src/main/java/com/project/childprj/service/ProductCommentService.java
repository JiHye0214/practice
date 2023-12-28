package com.project.childprj.service;

import com.project.childprj.domain.ProductComment;
import java.util.List;

public interface ProductCommentService {

    List<ProductComment> cmtList(Long productId);

    int cmtWrite(Long userId, Long productId, String content);

    int cmtRemove(Long commentId);
}
