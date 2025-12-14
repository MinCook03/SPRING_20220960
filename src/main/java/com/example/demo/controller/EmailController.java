package com.example.demo.controller;

import com.example.demo.model.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class EmailController {

    private final EmailService emailService;

    // 1. 메일 내용 받아서 파일로 저장 (POST 요청)
    @PostMapping("/upload-email")
    public String uploadEmail(@RequestParam("email") String email,
                              @RequestParam("subject") String subject,
                              @RequestParam("message") String message,
                              RedirectAttributes redirectAttributes) {
        try {
            // 서비스 호출: 프로젝트 내 static/upload 폴더에 저장됨
            emailService.saveEmailLog(email, subject, message);
            
            // 성공 메시지 전달 (리다이렉트 후에도 1회 유지됨)
            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");
            
            // 성공 시 완료 페이지로 리다이렉트 (새로고침 시 중복 전송 방지)
            return "redirect:/upload_end"; 
            
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 발생 시 처리 (에러 페이지로 이동하거나 메인으로)
            return "/error_page/article_error"; 
        }
    }

    // 2. 완료 화면 보여주기 (GET 요청)
    @GetMapping("/upload_end")
    public String showUploadEnd() {
        // templates/upload_end.html 파일을 찾아 보여줌
        return "upload_end"; 
    }
}