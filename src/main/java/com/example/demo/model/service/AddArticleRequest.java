package com.example.demo.model.service;

import com.example.demo.model.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // Getter, Setter, toString, equals 등 자동 생성
public class AddArticleRequest {

    private String title;
    private String content;
    private String user;
    
    // 아래 필드들은 글 수정 화면(Form)에서 넘어오지 않을 경우 null일 수 있습니다.
    // 글 작성(Insert) 시에는 필요하지만, 수정(Update) 시에는 보통 title과 content만 사용합니다.
    private String newdate;
    private String count;
    private String likec;

    // DTO -> Entity 변환 메서드 (주로 글 '작성' 시 사용됨)
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