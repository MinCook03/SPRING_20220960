package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA를 위한 기본 생성자
@AllArgsConstructor // @Builder가 사용할 모든 필드를 받는 생성자
@Builder // 클래스 레벨에 이것 하나만 선언
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title = "";

    @Column(name = "content", nullable = false)
    private String content = "";

    @Column(name = "user", nullable = false)
    private String user = "";

    @Column(name = "newdate", nullable = false)
    private String newdate = "";

    @Column(name = "count", nullable = false)
    private String count = "";

    @Column(name = "likec", nullable = false)
    private String likec = "";

    // update 메소드는 필요하므로 그대로 둡니다.
    @Builder
    public Board(string title, string content,string user, string newdate, string likec)
        this.title = title;



    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // === 수동으로 작성했던 builder(), user(), Board(title, content) 생성자 모두 삭제 ===
}