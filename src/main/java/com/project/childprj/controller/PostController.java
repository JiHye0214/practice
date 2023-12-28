package com.project.childprj.controller;

import com.project.childprj.domain.*;
import com.project.childprj.service.PostCommentService;
import com.project.childprj.service.PostService;
import com.project.childprj.service.UserService;
import com.project.childprj.util.U;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostCommentService postCommentService;

    // 글 목록
    @GetMapping("/list")
    public void postList(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                         @RequestParam(name = "sq", required = false, defaultValue = "") String sq,
                         @RequestParam(name = "postOrderWay", required = false, defaultValue = "최신순") String postOrderWay,
                         Model model,
                         HttpServletRequest request
    ){
        String uri = U.getRequest().getRequestURI();
        request.getSession().setAttribute("prevPage", uri);

        postService.list(page, sq, postOrderWay, model);
    }

    @GetMapping("/detail/{id}")
    public String marketDetail(@PathVariable(name = "id") Long id, Model model) {

        model.addAttribute("post", postService.postDetail(id));
        postService.incViewCnt(id);

        if(postService.postDetail(id) != null){
            List<PostComment> list = postCommentService.cmtList(id);
            boolean check = postService.clickCheck(U.getLoggedUser().getId(), id);

            model.addAttribute("check", check);
            model.addAttribute("postCmt", list);
            model.addAttribute("writerImg", userService.findUserImg(postService.postDetail(id).getUser().getId()));
            model.addAttribute("cmtWriterImg", userService.findUserImg(U.getLoggedUser().getId()));
        }

        return "post/detail";
    }

    @GetMapping("/write")
    public void postWrite(Model model){
        model.addAttribute("writerImg", userService.findUserImg(U.getLoggedUser().getId())); // 작성자 img
    }

    @GetMapping("/update/{id}")
    public String postUpdate(@PathVariable(name = "id") Long id, Model model) {
        Post post = postService.postDetail(id);
        model.addAttribute("post", post);
        model.addAttribute("writerImg", userService.findUserImg(U.getLoggedUser().getId()));
        return "post/update";
    }

    @PostMapping("/orderWay")
    public String orderWay(@RequestParam(name = "postOrderWay", required = false, defaultValue = "최신순") String postOrderWay,
                           @RequestParam(name = "sq", required = false, defaultValue = "") String sq,
                           RedirectAttributes redirectAttrs
    ) {
        redirectAttrs.addAttribute("postOrderWay", postOrderWay);
        redirectAttrs.addAttribute("sq", sq);

        return "redirect:/post/list";
    }

    @PostMapping("/search")
    public String search(@RequestParam(name = "postOrderWay", required = false, defaultValue = "최신순") String postOrderWay,
                         @RequestParam(name = "sq", required = false, defaultValue = "") String sq,
                         RedirectAttributes redirectAttrs
    ) {
        redirectAttrs.addAttribute("postOrderWay", postOrderWay);
        redirectAttrs.addAttribute("sq", sq);

        return "redirect:/post/list";
    }

    @PostMapping("/write")
    public String postWriteOk(
            Post post
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("title", post.getTitle());
            redirectAttrs.addFlashAttribute("content", post.getContent());

            return "redirect:/post/write";
        }

        model.addAttribute("result", postService.write(post));
        return "/post/writeOk";
    }

    @PostMapping("/update")
    public String postUpdateOk(
            Post post
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("title", post.getTitle());
            redirectAttrs.addFlashAttribute("content", post.getContent());

            return "redirect:/post/update/" + post.getId();
        }

        model.addAttribute("result", postService.update(post));
        return "post/updateOk";
    }

    @PostMapping("/cmtWrite")
    public String marketCmtWrite(PostComment postComment, Model model) {
        Long postId = postComment.getPostId();
        Long userId = U.getLoggedUser().getId();
        String content = postComment.getContent();

        model.addAttribute("change", postCommentService.cmtWrite(userId, postId, content));
        return "/post/success";
    }

    @PostMapping("/cmtDelete")
    public String marketCmtDel(PostComment postComment, Model model) {
        Long cmtId = postComment.getId();
        model.addAttribute("change", postCommentService.cmtRemove(cmtId));
        return "/post/success";
    }

    @PostMapping("/detailDelete")
    public String detailDelete(Post post, Model model) {
        Long postId = post.getId();
        model.addAttribute("delete", postService.detailDelete(postId));
        return "/post/deleteOk";
    }

    @PostMapping("/recommend")
    public String recommend(Post post, Model model){
        Long postId = post.getId();
        Long userId = U.getLoggedUser().getId();
        postService.recommend(userId, postId);
        return "/post/success";
    }

    @PostMapping("/opposite")
    public String opposite(Post post, Model model){
        Long postId = post.getId();
        Long userId = U.getLoggedUser().getId();
        postService.opposite(userId, postId);
        return "/post/success";
    }

}
