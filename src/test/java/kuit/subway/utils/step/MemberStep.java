package kuit.subway.utils.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.member.MemberRequest;
import kuit.subway.dto.response.auth.TokenResponse;

import static kuit.subway.study.common.CommonRestAssured.getWithToken;
import static kuit.subway.study.common.CommonRestAssured.post;
import static kuit.subway.utils.fixture.MemberFixture.회원_생성_요청;

public class MemberStep {

    public static final String MEMBER_PATH = "/members";

    public static ExtractableResponse<Response> 회원_생성(Integer age, String email, String password) {

        MemberRequest req = 회원_생성_요청(age, email, password);
        return post(MEMBER_PATH, req);
    }

    public static ExtractableResponse<Response> 내_회원_정보_요청(TokenResponse token) {
        return getWithToken(MEMBER_PATH + "/myinfo", token);
    }
}
