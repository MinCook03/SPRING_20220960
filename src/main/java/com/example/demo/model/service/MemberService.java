package com.example.demo.model.service;

import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // 필수 임포트
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // [수정] 암호화 객체 주입

    /**
     * 회원 가입 (비밀번호 암호화 저장)
     */
    public void saveMember(AddMemberRequest request) {
        // [수정] 입력받은 비밀번호를 암호화하여 DTO에 다시 설정
        // (AddMemberRequest에 @Data나 @Setter가 있어야 합니다)
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        // 암호화된 비밀번호가 담긴 객체를 DB에 저장
        memberRepository.save(request.toEntity());
    }

    /**
     * 로그인 체크 (암호화된 비밀번호 비교)
     */
    public Member loginCheck(String email, String rawPassword) {
        // 1. 이메일로 회원 조회 (Optional 처리)
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        // 2. 비밀번호 일치 여부 확인
        // passwordEncoder.matches(입력받은비번, DB에저장된암호화비번)
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member; // 인증 성공 시 회원 객체 반환
    }
}