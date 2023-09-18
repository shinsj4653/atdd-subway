package kuit.subway.dto.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import kuit.subway.domain.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRequest {

    @Positive
    private Integer age;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "이메일 형식이 올바르지 않습니다.")
    private String email;


    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[~!@#$%^&*()_+])[A-Za-z0-9~!@#$%^&*()_+]{8,}", message = "특수문자 포함 8자 이상 입력해야 합니다.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .age(this.age)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
