package com.example.demo.model.repository;

import com.example.demo.model.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // 기본적인 save, findById, findAll 등은 JpaRepository에 이미 포함되어 있습니다.

    // [추가 기능] 이메일로 회원 정보 찾기 (로그인 시 필요)
    // SQL: SELECT * FROM member WHERE email = ?
    Optional<Member> findByEmail(String email);

    // [추가 기능] 중복 가입 방지용 이메일 존재 여부 확인
    // SQL: SELECT count(*) FROM member WHERE email = ?
    boolean existsByEmail(String email);
}