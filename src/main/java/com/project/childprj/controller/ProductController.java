package com.project.childprj.controller;

import com.project.childprj.domain.Product;
import com.project.childprj.domain.ProductComment;
import com.project.childprj.domain.ProductValidator;
import com.project.childprj.domain.User;
import com.project.childprj.service.ProductCommentService;
import com.project.childprj.service.ProductService;
import com.project.childprj.service.UserService;
import com.project.childprj.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCommentService productCommentService;

    // 글 목록 조회
    @GetMapping("/list")
    public void list(Integer page, String searchTxt, Model model) {
        productService.list(page, searchTxt, model);
    }

    @PostMapping("/orderWay")
    public String orderWay(String orderWay, String searchTxt, RedirectAttributes redirectAttrs) {
        U.getSession().setAttribute("orderWay", orderWay);
        redirectAttrs.addAttribute("searchTxt", searchTxt);

        return "redirect:/product/list";
    }

    @PostMapping("/search")
    public String search(String searchTxt, RedirectAttributes redirectAttrs) {
        redirectAttrs.addAttribute("searchTxt", searchTxt);

        return "redirect:/product/list";
    }

    // 글 작성
    @GetMapping("/write")
    public void write() {
    }

    @PostMapping("/write")
    public String writeOk(
            @Valid Product product
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("price", product.getPrice());
            redirectAttrs.addFlashAttribute("productName", product.getProductName());
            redirectAttrs.addFlashAttribute("region", product.getRegion());
            redirectAttrs.addFlashAttribute("content", product.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for (FieldError err : errList) {
                redirectAttrs.addFlashAttribute("err_" + err.getField(), err.getCode());
            }

            return "redirect:/product/write";
        }

        model.addAttribute("result", productService.write(product));
        return "/product/writeOk";
    }

    // 글 상세
    @GetMapping("/detail/{id}")
    public String marketDetail(@PathVariable(name = "id") Long id, Model model) {
        List<ProductComment> list = productCommentService.cmtList(id);
        model.addAttribute("productCmt", list); // 특정 글의 댓글 모음
        model.addAttribute("product", productService.productDetail(id)); // 특정 글
        return "product/detail";
    }

    // 댓글 작성
    @PostMapping("/cmtWrite")
    public String marketCmtWrite(ProductComment productComment, Model model) {
        Long productId = productComment.getProductId();
        Long userId = U.getLoggedUser().getId();  // 세션 너란 녀석...
        String content = productComment.getContent();

        model.addAttribute("change", productCommentService.cmtWrite(userId, productId, content));
        return "/product/success";
    }

    // 댓글 삭제
    @PostMapping("/cmtDelete")
    public String marketCmtDel(ProductComment productComment, Model model) {
        Long cmtId = productComment.getId();
        model.addAttribute("change", productCommentService.cmtRemove(cmtId));
        return "/product/success";
    }

    // 글 삭제
    @PostMapping("/detailDelete")
    public String detailDelete(Product product, Model model) {
        Long productId = product.getId();
        model.addAttribute("change", productService.detailDelete(productId));
        return "/product/success";
    }

    // 글 수정
    @GetMapping("/update")
    public void update() {

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new ProductValidator());
    }
}