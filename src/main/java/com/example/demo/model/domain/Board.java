package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용 기본 생성자
@AllArgsConstructor // 빌더 패턴을 위한 모든 필드 생성자
@Builder // 클래스 레벨에 선언 (이것 하나면 충분합니다)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "user", nullable = false)
    private String user;

    @Column(name = "newdate", nullable = false)
    private String newdate;

    @Column(name = "count", nullable = false)
    private String count;

    @Column(name = "likec", nullable = false)
    private String likec;

  // [수정] 모든 필드를 받아서 업데이트하도록 변경
    public void update(String title, String content, String user, String newdate, String count, String likec) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.newdate = newdate;
        this.count = count;
        this.likec = likec;
    }
}
