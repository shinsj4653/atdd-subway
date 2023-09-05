package kuit.subway.dto.response.auth;

import kuit.subway.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginResponse {

    private Long id;
    private int age;
    private String email;

    public static MemberLoginResponse of(Member member) {
        return MemberLoginResponse.builder()
                .id(member.getId())
                .age(member.getAge())
                .email(member.getEmail())
                .build();
    }
}
