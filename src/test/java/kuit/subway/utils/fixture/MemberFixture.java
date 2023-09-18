package kuit.subway.utils.fixture;

import kuit.subway.dto.request.member.MemberRequest;

public class MemberFixture {
    public static MemberRequest 회원_생성_요청(Integer age, String email, String password) {
        return new MemberRequest(age, email, password);
    }
}
