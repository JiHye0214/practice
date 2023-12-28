package com.project.childprj.controller;

import com.project.childprj.service.ChildCenterService;
import com.project.childprj.service.ChildHouseService;
import com.project.childprj.service.KindergardenService;
import jakarta.servlet.http.HttpServletRequest;
import com.project.childprj.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/protect")
public class ProtectController {

    @Autowired
    private KindergardenService kindergardenService;

    @Autowired
    private ChildHouseService childHouseService;

    @Autowired
    private ChildCenterService childCenterService;

    @GetMapping("/list")
    public String protectList(
            Integer page
            , String type
            , Model model
            , HttpServletRequest request
            , RedirectAttributes redirectAttrs
    ) {
        String uri = U.getRequest().getRequestURI();
        request.getSession().setAttribute("prevPage", uri);

        if (type == null) type = "유치원";

        if (type.equals("유치원")) {
            kindergardenService.kindergardenList(page, type, model);
            return "protect/list";
        } else if (type.equals("어린이집")) {
            childHouseService.childHouseList(page, type, model);
            return "protect/list";
        } else if (type.equals("아동센터")) {
            childCenterService.childCenterList(page, type, model);
            return "protect/list";
        } else {
            redirectAttrs.addAttribute("type", "유치원");
            return "redirect:/protect/list";
        }
    }

    @PostMapping("/typeSelect")
    public String typeSelect(String type, RedirectAttributes redirectAttrs) {
        redirectAttrs.addAttribute("type", type);
        return "redirect:/protect/list";
    }

    @GetMapping("/detail/{type}/{id}")
    public String protectDetail(
            @PathVariable(name = "type") String type
            , @PathVariable(name = "id") Long id
            , Model model
            , HttpServletRequest request
            , RedirectAttributes redirectAttrs
    ) {
        String uri = U.getRequest().getRequestURI();
        request.getSession().setAttribute("prevPage", uri);

        if (type.equals("유치원")) {
            model.addAttribute("type", type);
            model.addAttribute("kindergarden", kindergardenService.getKindergarden(id));
        } else if (type.equals("어린이집")) {
            model.addAttribute("type", type);
            model.addAttribute("childHouse", childHouseService.getChildHouse(id));
        } else {
            redirectAttrs.addAttribute("type", "유치원");
            return "redirect:/protect/list";
        }

        return "protect/detail";
    }

}
