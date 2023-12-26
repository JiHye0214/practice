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

    // Autowired / GetMapping(RequestMapping) / PostMapping 구분해 놓기!
    // PostMapping 주석 간단 설명

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

        model.addAttribute("userImg", userService.findUserImg(U.getLoggedUser().getId()));
    }

    @GetMapping("/signUpAgree")
    public void signUpAgree(){
    }

    @GetMapping("/find")
    public String find(User user, Model model){
        model.addAttribute("user", user);
        return "/user/find";
    }

    // 로그인
    @PostMapping("/logIn")
    public void logInPost(){};

    // 로그인 에러
    @PostMapping("/loginError")
    public String loginError(){
        return "user/login";
    }

    // 찾기 - 아이디 (이메일)
    @PostMapping("/findId")
    public String findId(User user, Model model){
        String findName = user.getName();
        String findEmail = user.getEmail();
        model.addAttribute("findId", userService.findIdPwByEmail(findName, findEmail)); // boolean
        model.addAttribute("user", userService.userIdIs(findEmail));
        return "/user/find";
    }

    // 찾기 - 비번 (아이디)
    @PostMapping("/findPwById")
    public String findPwById(User user, Model model){
        String findName = user.getName();
        String findId = user.getLoginId();
        model.addAttribute("findPwId", userService.findPwById(findName, findId));
        return "user/find";
    }

    // 찾기 - 비번 (이메일)
    @PostMapping("/findPwByEmail")
    public String findPwByEmail(User user, Model model){
        String findName = user.getName();
        String findEmail = user.getEmail();
        model.addAttribute("findPwEmail", userService.findIdPwByEmail(findName, findEmail));
        return "user/find";
    }

    // 회원가입
    @PostMapping("/signUp")
    public String signUp(@Valid User user,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        // 검증 에러가 있으면 redirect
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

    // 마이페이지 - 프사 변경
    @PostMapping("/userImg") // @RequestParam : 얘는 name 값을 가져온다!!
    public String fixUserImg(@RequestParam Map<String, MultipartFile> file, Model model){
        model.addAttribute("change", userService.insertImg(file));
        return "user/changeSuccess";
    }

    // 마이페이지 - 닉네임 변경
    @PostMapping("/nickname")
    public String fixNickname(User user, Model model){

        U.getLoggedUser().setNickname(user.getNickname());
        model.addAttribute("change", userService.modifyNickname(user));
        return "/user/changeOk";
    }

    // 마이페이지 - 비번 변경
    @PostMapping("/password")
    public String fixPassword(User user, Model model){

        model.addAttribute("change", userService.modifyPassword(user));
        return "/user/changeOk";
    }

    // 마이페이지 - 회원 탈퇴
    @PostMapping("/drop")
    public String dropUser(User user, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 회원 탈퇴 후 로그아웃
        // 성공 !!
        if(passwordEncoder.matches(user.getPassword(), U.getLoggedUser().getPassword())){ // 비번 맞으면
            Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // 근데 이게 머지 인증 가져오기?
            if (auth != null) {
                model.addAttribute("delete", userService.dropUser(user));
                new SecurityContextLogoutHandler().logout(request, response, auth); // 시큐리티 여기서도 되네
            }
            return "redirect:/home";
        }
        return "/user/dropFail";
    }

    // 찜리스트
    @GetMapping("/zzim")
    public void togetherZzim(Integer page, Model model) {
        zzimService.zzimList(page, model);
    }

    // 찜 해제
//    @PostMapping("/deleteZzim")
//    public String deleteZzim(Zzim zzim, Long togetherId) {
//        // zzim 테이블에서 데이터 삭제
//        zzimService.deleteZzim(zzim, togetherId);
//
//        // together 테이블의 zzimCnt 변경
//        boolean isZzimCilked = togetherService.isZzimCheck(togetherId);
//        if (!isZzimCilked) {
//            togetherService.changeZzimCnt(1L, togetherId);
//        } else {
//            togetherService.changeZzimCnt(-1L, togetherId);
//        }
//
//        return "redirect:/user/zzim";
//    }

// ------------------validator--------------------

    @Autowired
    UserValidator userValidator;

    @InitBinder("User")
    public void intiBinder(WebDataBinder binder) {

        binder.setValidator(userValidator);
    }
}
