package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.domain.Article;
import com.example.demo.model.service.BlogService; // 기존 코드 유지
import com.example.demo.model.service.AddArticleRequest; // 새로운 기능 추가를 위해 import
import lombok.RequiredArgsConstructor; // @Autowired 대신 생성자 주입을 위해 추가

import java.util.List;

@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성 (Autowired 대체)
@Controller // 컨트롤러 어노테이션 명시
public class BlogController {

    // @Autowired 대신 final과 @RequiredArgsConstructor로 생성자 주입을 사용해 최적화
    private final BlogService blogService; // 기존 코드 유지

    // 이전에 요청하신 게시글 저장 및 리다이렉트 기능 추가
    /**
     * 새 게시글을 저장하고 게시글 목록 페이지로 리다이렉트합니다.
     * 
     * @param request 폼 데이터를 바인딩할 요청 객체
     * @return 리다이렉트할 경로 문자열
     */
    @PostMapping("/api/articles")
    public String addArticle(@ModelAttribute AddArticleRequest request) {

        // 1. 서비스 호출을 통해 게시글 저장
        blogService.save(request);

        // 2. 게시글 목록 페이지로 리다이렉션
        return "redirect:/article_list";
    }

    // 기존 코드 유지 (게시글 리스트 조회)
    @GetMapping("/article_list") // 게시판 링크 지정
    public String article_list(Model model) {
        List<Article> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("articles", list); // 모델에 추가
        return "article_list"; // .HTML 연결
    }

    // favicon.ico 처리 메서드 추가 (필수적인 최적화)
    @GetMapping("/favicon.ico")
    @ResponseBody
    public void favicon() {
        // 브라우저의 파비콘 요청에 대해 불필요한 뷰 리졸버 검색을 방지
    }
}