package com.prj1.controller;

import com.prj1.domain.Member;
import com.prj1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor

// /member/xxx
@RequestMapping("member")
public class MemberController {

    private final MemberService service;

    // 회원가입 페이지
    @GetMapping("signup")
    public String signupForm() {
        return "member/signup";
    }

    // 회원가입 핸들러
    @PostMapping("signup")
    public String signup(Member member) {
        service.signup(member);
        return "redirect:/";
    }

    // 전체 회원 목록 보기 (C"R"UD) : 어드민
    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("memberList", service.list());
        return "member/list";
    }

    // 회원 정보 보기 (C"R"UD) : 1인 정보
    @GetMapping("")
    public String info(Integer id, Model model) {
        model.addAttribute("member", service.get(id));
        return "member/info";
    }

    // 회원 탈퇴 기능
    @PostMapping("remove")
    public String remove(Integer id) {
        service.remove(id);
        return "redirect:/member/signup";
    }

    // 회원 정보 수정
    @GetMapping("modify")
    public String modifyForm(Integer id, Model model) {
        model.addAttribute("member", service.get(id));
        return "member/modify";
    }

    @PostMapping("modify")
    public String modify(Member member,
                         RedirectAttributes rttr) {
        service.modify(member);
        rttr.addAttribute("id", member.getId());
        return "redirect:/member";
    }

    @GetMapping("email")
    // 응답한 결과를 jsp 로 보지 않고
    // 결과 그 자체로 response 를 보냄
    @ResponseBody
    public String emailCheck(String email) {
//        System.out.println("email = " + email);
        String message = service.emailCheck(email);
        // 받은 message 는 Model 에 넣으면 안됨!!
        // 전체 페이지가 아닌 일부 페이지만 업데이트 하므로
        // view 로 넘기는 model 에 넣으면 안됨
        return message;
    }
}
