package com.project.childprj.service;

import com.project.childprj.domain.Post;
import com.project.childprj.domain.PostRecommend;
import com.project.childprj.domain.User;
import com.project.childprj.repository.PostRecommendRepository;
import com.project.childprj.repository.PostRepository;
import com.project.childprj.repository.UserRepository;
import com.project.childprj.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private UserRepository userRepository;
    private PostRepository postRepository;
    private PostRecommendRepository postRecommendRepository;

    @Autowired
    public PostServiceImpl(SqlSession sqlSession) {
        postRecommendRepository = sqlSession.getMapper(PostRecommendRepository.class);
        postRepository = sqlSession.getMapper(PostRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
    }

    @Override
    public List<Post> list(Integer page, String sq, String postOrderWay, Model model) {

        String validSq = sq;

        if (page < 1) page = 1;

        Integer pagesPerSection = 5;
        Integer rowsPerPage = 5;

        int totalLength = postRepository.selectCountAll(sq);
        if(totalLength == 0) validSq = "";
        int totalPage = (int) Math.ceil(totalLength / (double) rowsPerPage);

        int startPage = 0;
        int endPage = 0;

        List<Post> posts = new ArrayList<>();

        if (totalLength > 0) {
            if (page > totalPage) page = totalPage;

            int fromRow = (page - 1) * rowsPerPage;

            startPage = (((page - 1) / pagesPerSection) * pagesPerSection) + 1;
            endPage = startPage + pagesPerSection - 1;
            if (endPage > totalPage) endPage = totalPage;

            if (postOrderWay.equals("최신순")) {
                posts = postRepository.selectFromCntOrderByDate(fromRow, rowsPerPage, validSq);
            } else if (postOrderWay.equals("추천순")) {
                posts = postRepository.selectFromCntOrderByRcmCnt(fromRow, rowsPerPage, validSq);
            }
            model.addAttribute("posts", posts);
        } else {
            page = 0;
        }

        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("rowsPerPage", rowsPerPage);
        model.addAttribute("postOrderWay", postOrderWay);
        model.addAttribute("sq", sq);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return posts;
    }

    @Override
    public int write(Post post) {
        User user = U.getLoggedUser();

        user = userRepository.findUserById(user.getId());
        post.setUser(user);

        int cnt = postRepository.insert(post);

        return cnt;
    }

    @Override
    public Post postDetail(Long id) {
        return postRepository.findPostById(id);
    }

    @Override
    public void incViewCnt(Long id) {
        postRepository.incViewCnt(id);
    }

    @Override
    public int detailDelete(Long id) {
        return postRepository.detailDelete(id);
    }

    @Override
    public boolean clickCheck(Long userId, Long postId) {
        PostRecommend postRecommend = postRecommendRepository.clickCheck(userId, postId);
        return (postRecommend != null);
    }

    @Override
    public int recommend(Long userId, Long postId) {
        postRepository.incRecomCnt(postId);
        return postRecommendRepository.recommend(userId, postId);
    }

    @Override
    public int opposite(Long userId, Long postId) {
        postRepository.decRecomCnt(postId);
        return postRecommendRepository.opposite(userId, postId);
    }

    @Override
    public int update(Post post) {
        int result = postRepository.update(post);
        return result;
    }

    @Override
    public List<Post> selectFive() {
        return postRepository.selectFive();
    }

}
