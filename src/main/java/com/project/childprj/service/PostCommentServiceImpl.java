package com.project.childprj.service;

import com.project.childprj.domain.PostComment;
import com.project.childprj.repository.PostCommentRepository;
import com.project.childprj.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService{

    private UserRepository userRepository;
    private PostCommentRepository postCommentRepository;

    @Autowired
    public PostCommentServiceImpl(SqlSession sqlSession) {
        userRepository = sqlSession.getMapper(UserRepository.class);
        postCommentRepository = sqlSession.getMapper(PostCommentRepository.class);
    }

    @Override
    public List<PostComment> cmtList(Long postId) {
        return postCommentRepository.findCommentById(postId);
    }

    @Override
    public int cmtWrite(Long userId, Long postId, String content) {
        return postCommentRepository.cmtWrite(userId, postId, content);
    }

    @Override
    public int cmtRemove(Long commentId) {
        return postCommentRepository.cmtRemove(commentId);
    }
}
