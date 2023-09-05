package kuit.subway.dto.request.member;

import kuit.subway.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRequest {
    private Integer age;
    private String email;
    private String password;

    public Member toMember() {
        return new Member(age, email, password);
    }
}
