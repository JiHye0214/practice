package com.project.childprj.controller;

import com.project.childprj.service.TogetherService;
import com.project.childprj.service.ZzimService;
import com.project.childprj.util.U;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page
            , @RequestParam(name = "type", required = false, defaultValue = "체험") String type
            , Model model
            , HttpServletRequest request
            , RedirectAttributes redirectAttrs
    ) {
        String uri = U.getRequest().getRequestURI();
        request.getSession().setAttribute("prevPage", uri);

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
    public String togetherDetail(
            @PathVariable(name = "type") String type
            , @PathVariable(name = "id") Long id
            , Model model
            , HttpServletRequest request
            , RedirectAttributes redirectAttrs
    ) {
        String uri = U.getRequest().getRequestURI();
        request.getSession().setAttribute("prevPage", uri);

        if (type.equals("체험") || type.equals("축제") || type.equals("공연ㆍ예술")) {

            togetherService.togetherDetail(type, id, model);
            model.addAttribute("together", togetherService.getTogether(id));

            return "together/detail";
        } else {
            redirectAttrs.addAttribute("type", "체험");
            return "redirect:/together/list";
        }

    }

    @PostMapping("/listZzimToggle")
    public String listZzimToggle(
            @RequestParam(name = "togetherId", required = false) Long togetherId
            , @RequestParam(name = "type", required = false) String type
            , @RequestParam(name = "page", required = false) Long page
            , RedirectAttributes redirectAttrs
    ) {
        Long userId = U.getLoggedUser().getId();
        boolean isZzimChecked = zzimService.isZzimChecked(userId, togetherId);

        if (!isZzimChecked) {
            zzimService.insertZzim(userId, togetherId);
            togetherService.changeZzimCnt(1L, togetherId);
            togetherService.changeIsZzimClicked("true", togetherId);
        } else {
            zzimService.deleteZzim(userId, togetherId);
            togetherService.changeZzimCnt(-1L, togetherId);
            togetherService.changeIsZzimClicked("false", togetherId);
        }

        redirectAttrs.addAttribute("type", type);
        redirectAttrs.addAttribute("page", page);

        return "redirect:/together/list";
    }

    @PostMapping("/detailZzimToggle")
    public String detailZzimToggle(
            @RequestParam(name = "togetherId", required = false) Long togetherId
            , @RequestParam(name = "type", required = false) String type
            , RedirectAttributes redirectAttrs
    ) {
        Long userId = U.getLoggedUser().getId();
        boolean isZzimChecked = zzimService.isZzimChecked(userId, togetherId);

        if (!isZzimChecked) {
            zzimService.insertZzim(userId, togetherId);
            togetherService.changeZzimCnt(1L, togetherId);
            togetherService.changeIsZzimClicked("true", togetherId);
        } else {
            zzimService.deleteZzim(userId, togetherId);
            togetherService.changeZzimCnt(-1L, togetherId);
            togetherService.changeIsZzimClicked("false", togetherId);
        }

        redirectAttrs.addAttribute("type", type);
        redirectAttrs.addAttribute("togetherId", togetherId);

        return "redirect:/together/detail/{type}/{togetherId}";
    }

}
