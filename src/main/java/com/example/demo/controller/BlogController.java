package com.example.demo.controller;

import com.example.demo.model.domain.Article; 
import com.example.demo.model.domain.Board;   
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import jakarta.servlet.http.HttpSession; // 세션 관리
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate; // [필수] 날짜 생성을 위한 임포트
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;

    // ==========================================
    // 1. 게시글 목록 조회
    // ==========================================
    @GetMapping("/board_list")
    public String boardList(Model model, 
                            @RequestParam(defaultValue = "0") int page, 
                            @RequestParam(defaultValue = "") String keyword,
                            HttpSession session) { 
        
        // 1. 로그인 여부 확인
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; 
        }

        // 2. 화면에 보여줄 이메일 전달
        String email = (String) session.getAttribute("email"); 
        model.addAttribute("email", email); 

        // 3. 게시글 목록 조회
        int pageSize = 5; 
        PageRequest pageable = PageRequest.of(page, pageSize); 
        Page<Board> list; 

        if (keyword.isBlank()) {
            list = blogService.findAll(pageable);
        } else {
            list = blogService.searchByKeyword(keyword, pageable);
        }

        int startNum = (page * pageSize) + 1;

        model.addAttribute("boards", list);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startNum", startNum);

        return "board_list";
    }

    // ==========================================
    // 2. 글 쓰기 (화면 이동)
    // ==========================================
    @GetMapping("/board_write")
    public String showWriteForm(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        // 작성자 칸 자동 입력을 위해 이메일 전달
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);

        return "board_write";
    }

    // ==========================================
    // 3. 글 쓰기 (저장) - [오류 해결 핵심] 날짜/초기값 생성
    // ==========================================
    @PostMapping("/api/boards")
    public String addBoard(@ModelAttribute AddArticleRequest request, HttpSession session) {
        // 1. 로그인 세션 확인
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login"; 
        }

        // 2. 작성자(user) 강제 주입
        request.setUser(email);

        // 3. [오류 해결] 날짜(newdate) 생성 (오늘 날짜)
        // HTML에서 보내지 않으므로 서버에서 만들어줘야 합니다.
        request.setNewdate(LocalDate.now().toString()); 

        // 4. [오류 해결] 조회수(count), 추천수(likec) 초기화
        // DB에 null이 들어가지 않도록 "0"으로 설정
        request.setCount("0");
        request.setLikec("0");

        // 5. 저장
        blogService.save(request);
        return "redirect:/board_list";
    }

    // ==========================================
    // 4. 글 수정 (화면)
    // ==========================================
    @GetMapping("/board_edit/{id}")
    public String showEditForm(Model model, @PathVariable Long id) {
        Board board = blogService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        model.addAttribute("article", board); 
        return "board_edit"; 
    }

    // ==========================================
    // 5. 글 수정 (저장)
    // ==========================================
    @PutMapping("/api/board_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/board_list"; 
    }

    // ==========================================
    // 6. 글 삭제
    // ==========================================
    @DeleteMapping("/api/board_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    // ==========================================
    // 7. 게시글 상세 조회
    // ==========================================
    @GetMapping("/board_view/{id}")
    public String getBoardView(@PathVariable Long id, Model model, HttpSession session) {
        // 1. 게시글 찾기
        Board board = blogService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        model.addAttribute("board", board);
        
        // 2. 본인 확인용(수정/삭제 버튼 표시용) 로그인 유저 정보 전달
        String loginUser = (String) session.getAttribute("email");
        model.addAttribute("loginUser", loginUser);

        return "board_view";
    }

    // ==========================================
    // 8. 유틸리티 및 예외 처리
    // ==========================================
    @GetMapping("/article_list")
    public String articleList(Model model) {
        List<Article> list = blogService.findAllArticles(); 
        model.addAttribute("articles", list);
        return "article_list";
    }

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnNoFavicon() { }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(Model model) {
        model.addAttribute("errorMessage", "잘못된 접근입니다. ID는 숫자여야 합니다.");
        return "/error_page/article_error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFound(Model model, IllegalArgumentException ex) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "/error_page/article_error";
    }
}