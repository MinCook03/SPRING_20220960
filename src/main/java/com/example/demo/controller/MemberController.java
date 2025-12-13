package com.example.demo.controller;

import com.example.demo.model.domain.Member; // [필수] Member 객체를 쓰기 위해 추가
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    // ==========================================
    // 1. 회원 가입 페이지 이동
    // ==========================================
    @GetMapping("/join_new")
    public String join_new() {
        return "join_new";
    }

    // ==========================================
    // 2. 회원 가입 요청 처리
    // ==========================================
    @PostMapping("/api/members")
    public String addMembers(@ModelAttribute AddMemberRequest request) {
        memberService.saveMember(request);
        return "join_end";
    }

    // ==========================================
    // 3. 로그인 페이지 이동
    // ==========================================
    @GetMapping("/member_login") // 요청하신 경로
    public String member_login() {
        return "login"; // login.html 연결
    }

    // ==========================================
    // 4. 로그인 체크 (핵심 로직)
    // ==========================================
    @PostMapping("/api/login_check")
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model) {
        try {
            // 아이디와 비번을 서비스로 넘겨 체크
            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            
            // 로그인 성공 시 세션 대신 모델에 담거나 리다이렉트 (여기선 요청대로 리스트 이동)
            model.addAttribute("member", member); 
            return "redirect:/board_list"; 
            
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 에러 메시지를 담아서 다시 로그인 페이지로
            model.addAttribute("error", e.getMessage());
            return "login"; 
        }
    }
}