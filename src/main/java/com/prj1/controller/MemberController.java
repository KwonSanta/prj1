package com.prj1.controller;

import com.prj1.domain.Member;
import com.prj1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // 회원 목록 보기 (C"R"UD) : 어드민
    @GetMapping("list")
    public String list(Model model) {
        model.addAttribute("memberList", service.list());
        return "member/list";
    }
    // 회원 정보 보기 (C"R"UD) : 자기 정보
}
