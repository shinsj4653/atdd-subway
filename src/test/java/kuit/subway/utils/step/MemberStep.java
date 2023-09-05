package kuit.subway.utils.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.member.MemberRequest;

import static kuit.subway.study.common.CommonRestAssured.post;
import static kuit.subway.utils.fixture.LineFixture.지하철_노선_생성_요청;
import static kuit.subway.utils.fixture.MemberFixture.회원_생성_요청;

public class MemberStep {

    public static final String MEMBER_PATH = "/members";

    public static ExtractableResponse<Response> 회원_생성(Integer age, String email, String password) {

        MemberRequest req = 회원_생성_요청(age, email, password);
        return post(MEMBER_PATH, req);
    }


}
