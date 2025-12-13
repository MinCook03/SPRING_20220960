package com.example.demo.controller;

import com.example.demo.model.domain.Article; // 레거시용 (필요 없다면 삭제 가능)
import com.example.demo.model.domain.Board;   // [중요] Board 임포트 확인
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;

   // ==========================================
    // 1. 게시글 목록 조회 (수정됨: 글 번호 계산 로직 추가)
    // ==========================================
    @GetMapping("/board_list")
    public String boardList(Model model, 
                            @RequestParam(defaultValue = "0") int page, 
                            @RequestParam(defaultValue = "") String keyword) {
        
        int pageSize = 5; // 페이지당 게시글 수 (변수로 관리)
        PageRequest pageable = PageRequest.of(page, pageSize); 
        Page<Board> list; 

        if (keyword.isBlank()) {
            list = blogService.findAll(pageable);
        } else {
            list = blogService.searchByKeyword(keyword, pageable);
        }

        // [추가 기능] 시작 번호 계산: (현재페이지 * 페이지사이즈) + 1
        // 예: 0페이지 -> 1, 1페이지 -> 6, 2페이지 -> 11 ...
        int startNum = (page * pageSize) + 1;

        model.addAttribute("boards", list);
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        
        // [추가] 뷰로 시작 번호 전달
        model.addAttribute("startNum", startNum);

        return "board_list";
    }

    // ==========================================
    // 2. 글 쓰기 (화면 및 저장)
    // ==========================================
    @GetMapping("/board_write")
    public String showWriteForm() {
        return "board_write";
    }

    @PostMapping("/api/boards")
    public String addBoard(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return "redirect:/board_list";
    }

    // ==========================================
    // 3. 글 수정 (화면 요청) - [수정됨]
    // ==========================================
    @GetMapping("/board_edit/{id}")
    public String showEditForm(Model model, @PathVariable Long id) {
        // [수정] Service가 Board를 반환하므로 자료형을 Board로 변경해야 합니다.
        Board board = blogService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        // HTML(board_edit.html)에서 th:object="${article}"을 쓰고 있다면 이름을 "article"로 유지해야 합니다.
        // 하지만 객체 자체는 Board입니다.
        model.addAttribute("article", board); 
        return "board_edit"; 
    }

    // ==========================================
    // 4. 글 수정 (데이터 저장 요청)
    // ==========================================
    @PutMapping("/api/board_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/board_list"; 
    }

    // ==========================================
    // 5. 글 삭제
    // ==========================================
    @DeleteMapping("/api/board_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }

    // ==========================================
    // 6. 게시글 상세 조회 (board_view) - [수정됨]
    // ==========================================
    @GetMapping("/board_view/{id}")
    public String getBoardView(@PathVariable Long id, Model model) {
        // [수정] Article -> Board 로 변경
        Board board = blogService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        
        // board_view.html에서 ${board}를 사용하므로 이름은 "board"로 저장
        model.addAttribute("board", board);
        
        return "board_view";
    }

    // ==========================================
    // 7. 유틸리티 및 예외 처리
    // ==========================================
    
    // (레거시 지원) Article 전체 목록 조회
    @GetMapping("/article_list")
    public String articleList(Model model) {
        // Service에 findAllArticles() 메서드가 있어야 오류가 나지 않습니다.
        // 만약 Article 기능을 뺐다면 이 메서드 자체를 삭제하세요.
        List<Article> list = blogService.findAllArticles(); 
        model.addAttribute("articles", list);
        return "article_list";
    }

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
    }

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