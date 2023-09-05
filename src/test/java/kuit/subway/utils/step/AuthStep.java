package kuit.subway.utils.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.auth.TokenRequest;
import kuit.subway.dto.request.line.LineCreateRequest;

import static kuit.subway.study.common.CommonRestAssured.post;
import static kuit.subway.utils.fixture.AuthFixture.토큰_생성_요청;
import static kuit.subway.utils.fixture.LineFixture.지하철_노선_생성_요청;

public class AuthStep {

    public static final String LOGIN_PATH = "/login";

    public static ExtractableResponse<Response> 로그인_회원_토근_생성(String email, String password) {

        TokenRequest req = 토큰_생성_요청(email, password);
        return post(LOGIN_PATH + "/token", req);
    }

}
