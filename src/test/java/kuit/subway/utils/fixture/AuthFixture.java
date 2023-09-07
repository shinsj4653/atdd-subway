package kuit.subway.utils.fixture;

import kuit.subway.dto.request.auth.LoginRequest;

public class AuthFixture {
    public static LoginRequest 토큰_생성_요청(String email, String password) {
        return new LoginRequest(email, password);
    }


}
