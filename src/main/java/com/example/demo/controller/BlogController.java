package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.service.BlogService; // 기존 코드 유지
import com.example.demo.model.service.AddArticleRequest; // 새로운 기능 추가를 위해 import
import lombok.RequiredArgsConstructor; // @Autowired 대신 생성자 주입을 위해 추가

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    public String board_list(Model model) {
        List<Board> list = blogService.findAllbBoards(); // 게시판 전체 리스트, 기존 Article에서 Board로 변경됨
        model.addAttribute("boards", list); // 모델에 추가
        return "board_list"; // .HTML 연결
    }


    // favicon.ico 처리 메서드 추가 (필수적인 최적화)
    @GetMapping("/favicon.ico")
    @ResponseBody
    public void favicon() {
        // 브라우저의 파비콘 요청에 대해 불필요한 뷰 리졸버 검색을 방지
    }

    @GetMapping("/board_edit/{id}") // 게시판 링크 지정
    public String article_edit(Model model, @PathVariable Long id) {
        Optional<Article> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/article_error"; // 오류 처리 페이지로 연결(이름 수정됨)
        }
        return "article_edit"; // .HTML 연결
    }

    @ControllerAdvice // 이 클래스가 전역 컨트롤러 설정 클래스임을 선언합니다.
    public class GlobalExceptionHandler {

        /**
         * URL의 파라미터 값이 컨트롤러 메소드에서 요구하는 타입과 맞지 않을 때 발생하는
         * MethodArgumentTypeMismatchException을 처리합니다.
         * (예: Long 타입이 필요한데 "6444fff" 같은 문자열이 들어온 경우)
         */
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public String handleTypeMismatch(Model model, MethodArgumentTypeMismatchException ex) {
            // 에러 페이지에 전달할 메시지를 모델에 추가합니다.
            model.addAttribute("errorMessage", "요청하신 페이지의 ID 형식이 올바르지 않습니다. 숫자만 입력해주세요.");
            
            // 보여줄 에러 페이지의 HTML 파일 이름을 반환합니다.
            return "/error_page/article_error";
        }
    }

    @PutMapping("/api/board_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list"; // 글 수정 이후 .html 연결
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }


    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findByIdboards(id); // 선택한 게시판 글
        
        if (list.isPresent()) {
            model.addAttribute("boards", list.get()); // 존재할 경우 실제 Board 객체를 모델에 추가
        } else {
            // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/article_error"; // 오류 처리 페이지로 연결
    }
    return "board_view"; // .HTML 연결
}
}