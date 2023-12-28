package com.project.childprj.controller;

import com.project.childprj.domain.*;
import com.project.childprj.service.TogetherService;
import com.project.childprj.service.UserService;
import com.project.childprj.service.ZzimService;
import com.project.childprj.util.U;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TogetherService togetherService;

    @Autowired
    private ZzimService zzimService;

    @GetMapping("/logIn")
    public String logIn(){
        return "/user/logIn";
    };

    @GetMapping("/signUp")
    public void signUp(){};

    @GetMapping("/mypage")
    public void mypage(Model model){

        Long id = U.getLoggedUser().getId();
        UserImg userImg = userService.findUserImg(id);
        model.addAttribute("userImg", userImg);
    }

    @GetMapping("/signUpAgree")
    public void signUpAgree(){
    }

    @GetMapping("/find")
    public String find(User user, Model model){
        model.addAttribute("user", user);
        return "/user/find";
    }

    @GetMapping("/zzim")
    public void togetherZzim(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            Model model
    ) {
        zzimService.zzimList(page, model);
    }

    @PostMapping("/logIn")
    public void logInPost(){};

    @PostMapping("/loginError")
    public String loginError(){
        return "user/login";
    }

    @PostMapping("/findId")
    public String findId(User user, Model model){
        String findName = user.getName();
        String findEmail = user.getEmail();
        model.addAttribute("findId", userService.findIdPwByEmail(findName, findEmail));
        model.addAttribute("user", userService.userIdIs(findEmail));
        return "/user/find";
    }

    @PostMapping("/findPwById")
    public String findPwById(User user, Model model){
        String findName = user.getName();
        String findId = user.getLoginId();
        model.addAttribute("findPwId", userService.findPwById(findName, findId));
        return "user/find";
    }

    @PostMapping("/findPwByEmail")
    public String findPwByEmail(User user, Model model){
        String findName = user.getName();
        String findEmail = user.getEmail();
        model.addAttribute("findPwEmail", userService.findIdPwByEmail(findName, findEmail));
        return "user/find";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid User user,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("loginId", user.getLoginId());
            redirectAttributes.addFlashAttribute("email", user.getEmail());
            redirectAttributes.addFlashAttribute("nickname", user.getNickname());
            redirectAttributes.addFlashAttribute("password", user.getPassword());
            redirectAttributes.addFlashAttribute("name", user.getName());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList){
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/user/signUp";
        }

        int resister = userService.signUp(user);
        model.addAttribute("result", resister);
        return "/user/signUpOk";
    }

    @PostMapping("/userImg") // @RequestParam : 얘는 name 값을 가져온다!!
    public String fixUserImg(@RequestParam Map<String, MultipartFile> file, Model model){
        model.addAttribute("change", userService.insertImg(file));
        return "user/changeSuccess";
    }

    @PostMapping("/nickname")
    public String fixNickname(User user, Model model){

        U.getLoggedUser().setNickname(user.getNickname());
        model.addAttribute("change", userService.modifyNickname(user));
        return "/user/changeOk";
    }

    @PostMapping("/password")
    public String fixPassword(User user, Model model){

        model.addAttribute("change", userService.modifyPassword(user));
        return "/user/changeOk";
    }

    @PostMapping("/drop")
    public String dropUser(User user, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if(passwordEncoder.matches(user.getPassword(), U.getLoggedUser().getPassword())){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                model.addAttribute("delete", userService.dropUser(user));
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            return "redirect:/home";
        }
        return "/user/dropFail";
    }

    @PostMapping("/deleteZzim")
    public String deleteZzim(Long userId, Long togetherId) {
        zzimService.deleteZzim(userId, togetherId);

        return "redirect:/user/zzim";
    }

    @InitBinder("user")
    public void intiBinder(WebDataBinder binder) {
        binder.setValidator(new UserValidator(userService));
    }
}
