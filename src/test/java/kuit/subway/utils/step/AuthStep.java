package kuit.subway.utils.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.auth.LoginRequest;
import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.utils.fixture.GithubFixture;

import static kuit.subway.study.common.CommonRestAssured.post;
import static kuit.subway.utils.fixture.AuthFixture.토큰_생성_요청;

public class AuthStep {

    public static final String AUTH_PATH = "/auth";

    public static ExtractableResponse<Response> 로그인_회원_토근_생성(String email, String password) {

        LoginRequest req = 토큰_생성_요청(email, password);
        return post(AUTH_PATH + "/login/token", req);
    }

}
