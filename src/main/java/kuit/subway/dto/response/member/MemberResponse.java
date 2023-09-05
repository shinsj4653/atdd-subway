package kuit.subway.dto.response.member;

import kuit.subway.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private Integer age;
    private String email;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .age(member.getAge())
                .email(member.getEmail())
                .build();
    }
}
