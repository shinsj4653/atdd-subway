package kuit.subway.dto.request.member;

import kuit.subway.domain.Member;
import lombok.*;
import org.checkerframework.checker.index.qual.Positive;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRequest {

    @Positive
    private Integer age;


    private String email;


    private String password;

    public Member toEntity() {
        return Member.builder()
                .age(this.age)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
