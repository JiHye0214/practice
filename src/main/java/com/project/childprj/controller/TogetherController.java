package com.project.childprj.controller;

import com.project.childprj.domain.Zzim;
import com.project.childprj.service.TogetherService;
import com.project.childprj.service.ZzimService;
import com.project.childprj.util.U;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/together")
public class TogetherController {

    @Autowired
    private TogetherService togetherService;

    @Autowired
    private ZzimService zzimService;

    @GetMapping("/list")
    public String togetherList(
            Integer page
            , String type
            , Model model
            , HttpServletRequest request
            , RedirectAttributes redirectAttrs
    ) {
        String uri = U.getRequest().getRequestURI();
        request.getSession().setAttribute("prevPage", uri);

        if (type == null) type = "체험";

        if (type.equals("체험") || type.equals("축제") || type.equals("공연ㆍ예술")) {
            togetherService.togetherList(page, type, model);
            return "together/list";
        } else {
            redirectAttrs.addAttribute("type", "체험");
            return "redirect:/together/list";
        }
    }

    @PostMapping("/typeSelect")
    public String typeSelect(String type, RedirectAttributes redirectAttrs) {
        redirectAttrs.addAttribute("type", type);

        return "redirect:/together/list";
    }

    @GetMapping("/detail/{type}/{id}")
    public String protectDetail(
            @PathVariable String type
            , @PathVariable Long id
            , Model model
            , HttpServletRequest request
            , RedirectAttributes redirectAttrs
    ) {
        String uri = U.getRequest().getRequestURI();
        request.getSession().setAttribute("prevPage", uri);

        if (type.equals("체험") || type.equals("축제") || type.equals("공연ㆍ예술")) {
            model.addAttribute("type", type);
            model.addAttribute("together", togetherService.getTogether(id));
        } else {
            redirectAttrs.addAttribute("type", "체험");
            return "redirect:/together/list";
        }

        return "together/detail";
    }

    // 찜 추가 & 해제
    @PostMapping("/toggleZzim")
    public String addZzim(Long togetherId, Long page, String type, RedirectAttributes redirectAttrs) {
        Long userId = U.getLoggedUser().getId();
        boolean isZzimChecked = zzimService.isZzimChecked(userId, togetherId);

//        System.out.println(togetherId);
//        System.out.println(isZzimChecked);

        if (!isZzimChecked) {
            zzimService.insertZzim(userId, togetherId); // zzim 테이블에 데이터 추가 or 삭제
            togetherService.changeZzimCnt(1L, togetherId); // together 테이블의 zzimCnt +1 or -1 (home 의 그래프 hot 5 정렬 위해)
        } else {
            zzimService.deleteZzim(userId, togetherId);
            togetherService.changeZzimCnt(-1L, togetherId);
        }

        redirectAttrs.addAttribute("page", page);
        redirectAttrs.addAttribute("type", type);

        return "redirect:/together/list";
    }

}
