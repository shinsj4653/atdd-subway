package kuit.subway.utils.fixture;

import kuit.subway.dto.request.auth.TokenRequest;

public class AuthFixture {
    public static TokenRequest 토큰_생성_요청(String email, String password) {
        return new TokenRequest(email, password);
    }


}
