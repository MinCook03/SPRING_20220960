package com.example.demo.model.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailService {

    public void saveEmailLog(String email, String subject, String message) throws IOException {
        
        // 1. 현재 프로젝트의 절대 경로(루트) 가져오기
        String projectPath = System.getProperty("user.dir");
        
        // 2. 저장할 경로 설정: src/main/resources/static/upload
        // (이 경로는 프로젝트 소스 폴더 내부입니다)
        Path uploadPath = Paths.get(projectPath, "src", "main", "resources", "static", "upload");

        // 3. 폴더가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("폴더 생성 완료: " + uploadPath.toString());
        }

        // 4. 파일명 생성 (이메일_시간.txt)
        String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
        String fileName = sanitizedEmail + "_" + System.currentTimeMillis() + ".txt";
        
        // 5. 전체 파일 경로 완성
        File saveFile = uploadPath.resolve(fileName).toFile();

        // 6. 콘솔에 저장 위치 출력 (확인용)
        System.out.println("==========================================");
        System.out.println("파일 저장 위치: " + saveFile.getAbsolutePath());
        System.out.println("==========================================");

        // 7. 파일 쓰기
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            writer.write("보낸 사람: " + email);
            writer.newLine();
            writer.write("제목: " + subject);
            writer.newLine();
            writer.write("내용:");
            writer.newLine();
            writer.write(message);
            writer.flush();
        }
    }
}