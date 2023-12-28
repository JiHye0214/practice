package com.project.childprj.repository;

import com.project.childprj.domain.ProductComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentRepository {

    List<ProductComment> findCommentById(Long productId);

    ProductComment findOneCmtById(Long commentId);

    int cmtWrite(Long userId, Long productId, String content);

    int cmtRemove(Long commentId);

}
