package com.project.childprj.controller;

import com.project.childprj.domain.Product;
import com.project.childprj.domain.ProductComment;
import com.project.childprj.service.ProductCommentService;
import com.project.childprj.service.ProductService;
import com.project.childprj.service.UserService;
import com.project.childprj.util.U;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCommentService productCommentService;

    @GetMapping("/list")
    public void list(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                     @RequestParam(name = "sq", required = false, defaultValue = "") String sq,
                     @RequestParam(name = "productOrderWay", required = false, defaultValue = "최신순") String productOrderWay,
                     Model model,
                     HttpServletRequest request
    ) {
        String uri = U.getRequest().getRequestURI();
        request.getSession().setAttribute("prevPage", uri);

        productService.list(page, sq, productOrderWay, model);
    }

    @GetMapping("/detail/{id}")
    public String marketDetail(@PathVariable(name = "id") Long id, Model model) {

        model.addAttribute("product", productService.productDetail(id));
        productService.incViewCnt(id);

        if(productService.productDetail(id) != null){
            List<ProductComment> list = productCommentService.cmtList(id);
            model.addAttribute("productCmt", list);
            model.addAttribute("writerImg", userService.findUserImg(productService.productDetail(id).getUser().getId()));
            model.addAttribute("cmtWriterImg", userService.findUserImg(U.getLoggedUser().getId()));
        }

        return "product/detail";
    }

    @GetMapping("/write")
    public void write(Model model) {
        model.addAttribute("writerImg", userService.findUserImg(U.getLoggedUser().getId())); // 작성자 img
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.productDetail(id);
        product.setProductImg(productService.findByProduct(id));
        model.addAttribute("product", product);
        model.addAttribute("writerImg", userService.findUserImg(productService.productDetail(id).getUser().getId()));
        model.addAttribute("cmtWriterImg", userService.findUserImg(U.getLoggedUser().getId()));
        return "product/update";
    }

    @PostMapping("/orderWay")
    public String orderWay(
            @RequestParam(name = "productOrderWay", required = false, defaultValue = "최신순") String productOrderWay
            , @RequestParam(name = "sq", required = false, defaultValue = "") String sq
            , RedirectAttributes redirectAttrs
    ) {
        redirectAttrs.addAttribute("productOrderWay", productOrderWay);
        redirectAttrs.addAttribute("sq", sq);

        return "redirect:/product/list";
    }

    @PostMapping("/search")
    public String search(@RequestParam(name = "productOrderWay", required = false, defaultValue = "최신순") String productOrderWay,
                         @RequestParam(name = "sq", required = false, defaultValue = "") String sq,
                         RedirectAttributes redirectAttrs
    ) {
        redirectAttrs.addAttribute("productOrderWay", productOrderWay);
        redirectAttrs.addAttribute("sq", sq);

        return "redirect:/product/list";
    }

    @PostMapping("/write")
    public String writeOk(
            Product product
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
            ,@RequestParam Map<String, MultipartFile> file
    ) {

        model.addAttribute("result", productService.write(product));
        model.addAttribute("insert", productService.imgInsert(file, product.getId()));
        return "product/writeOk";
    }

    @PostMapping("/update")
    public String updateOk(
            Product product
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
            ,@RequestParam Map<String, MultipartFile> file
    ) {
        model.addAttribute("insert", productService.imgInsert(file, product.getId()));
        return "product/updateOk";
    }

    @PostMapping("/cmtWrite")
    public String marketCmtWrite(ProductComment productComment, Model model) {
        Long productId = productComment.getProductId();
        Long userId = U.getLoggedUser().getId();
        String content = productComment.getContent();

        model.addAttribute("change", productCommentService.cmtWrite(userId, productId, content));
        return "/product/success";
    }

    @PostMapping("/cmtDelete")
    public String marketCmtDel(ProductComment productComment, Model model) {
        Long cmtId = productComment.getId();
        model.addAttribute("change", productCommentService.cmtRemove(cmtId));
        return "/product/success";
    }

    @PostMapping("/detailDelete")
    public String detailDelete(Product product, Model model) {
        Long productId = product.getId();
        model.addAttribute("delete", productService.detailDelete(productId));
        return "/product/deleteOk";
    }

}
