package com.example.demo.controller;

import com.example.demo.model.domain.Member;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;

import jakarta.servlet.http.Cookie; // [필수] 쿠키 제어
import jakarta.servlet.http.HttpServletRequest; // [필수] 요청 정보
import jakarta.servlet.http.HttpServletResponse; // [필수] 응답(쿠키설정)
import jakarta.servlet.http.HttpSession; 
import jakarta.validation.Valid; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID; 

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    // ==========================================
    // 1. 회원 가입 페이지 이동
    // ==========================================
    @GetMapping("/join_new")
    public String join_new(@ModelAttribute("request") AddMemberRequest request) {
        return "join_new";
    }

    // ==========================================
    // 2. 회원 가입 요청 처리
    // ==========================================
    @PostMapping("/api/members")
    public String addMembers(@Valid @ModelAttribute AddMemberRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "join_new"; 
        }
        memberService.saveMember(request);
        return "join_end";
    }

    // ==========================================
    // 3. 로그인 페이지 이동
    // ==========================================
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ==========================================
    // 4. 로그인 체크 (단일 사용자 로그인 처리)
    // ==========================================
    @PostMapping("/api/login_check")
    public String checkMembers(@ModelAttribute AddMemberRequest request, Model model, 
                               HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            // 기존 세션이 있다면 삭제 (단일 로그인)
            HttpSession session = httpRequest.getSession(false);
            if (session != null) {
                session.invalidate(); 
                Cookie cookie = new Cookie("JSESSIONID", null); 
                cookie.setPath("/"); 
                cookie.setMaxAge(0); 
                httpResponse.addCookie(cookie); 
            }
            
            // 새 세션 생성
            session = httpRequest.getSession(true);

            Member member = memberService.loginCheck(request.getEmail(), request.getPassword());
            
            String sessionId = UUID.randomUUID().toString();
            session.setAttribute("userId", sessionId);
            session.setAttribute("email", member.getEmail());

            return "redirect:/board_list";
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login"; 
        }
    }

    // ==========================================
    // 5. 로그아웃 (쿠키 삭제 및 세션 초기화 구현)
    // ==========================================
    @GetMapping("/api/logout") 
    public String member_logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 기존 세션 가져오기
            HttpSession session = request.getSession(false); 
            if (session != null) {
                session.invalidate(); // 기존 세션 무효화
            }

            // 2. 쿠키(JSESSIONID) 삭제
            Cookie cookie = new Cookie("JSESSIONID", null); 
            cookie.setPath("/"); // 쿠키 경로 설정
            cookie.setMaxAge(0); // 수명을 0으로 설정하여 즉시 삭제
            response.addCookie(cookie); // 응답 객체에 쿠키 추가

            // 3. 새로운 세션 생성 (초기화 확인용)
            session = request.getSession(true); 
            System.out.println("로그아웃 후 세션 userId 확인(null이어야 함): " + session.getAttribute("userId")); 

            // 4. 로그인 페이지로 리다이렉트
            // (return "login"은 화면만 보여주므로, URL 변경을 위해 redirect 권장)
            return "index"; 
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); 
            return "redirect:/login"; 
        }
    }
}