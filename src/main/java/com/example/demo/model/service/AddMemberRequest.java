package com.example.demo.model.service;

import com.example.demo.model.domain.Member;
import jakarta.validation.constraints.*; // 유효성 검사 어노테이션 임포트
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddMemberRequest {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "이름에는 특수문자를 사용할 수 없습니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    // 정규식: 최소 8자 이상, 대문자(A-Z)와 소문자(a-z)를 각각 최소 1개 이상 포함
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "비밀번호는 8자 이상이어야 하며, 대문자와 소문자를 모두 포함해야 합니다.")
    private String password;

    // 나이 검증을 위해 String -> Integer로 변경하는 것이 좋습니다.
    @NotNull(message = "나이는 필수 입력 값입니다.")
    @Min(value = 19, message = "나이는 19세 이상이어야 합니다.")
    @Max(value = 100, message = "나이는 100세 이하여야 합니다.")
    private Integer age;

    // "공백 O" 이므로 유효성 검사 어노테이션 생략 (Optional)
    private String mobile;

    // "공백 O" 이므로 유효성 검사 어노테이션 생략 (Optional)
    private String address;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(String.valueOf(age)) // Integer -> String 변환 (Entity가 String일 경우)
                .mobile(mobile)
                .address(address)
                .build();
    }
}