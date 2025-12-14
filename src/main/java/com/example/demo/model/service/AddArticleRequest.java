package com.example.demo.model.service;

import com.example.demo.model.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; // [필수] Controller에서 값을 넣으려면 Setter가 필요함

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter // [중요] Setter 추가
public class AddArticleRequest {

    private String title;
    private String content;
    private String user;
    private String newdate;
    private String count;
    private String likec;

    // DTO -> Entity 변환 메서드
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .newdate(newdate)
                .count(count)
                .likec(likec)
                .build();
    }
}