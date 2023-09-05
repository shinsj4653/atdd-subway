package kuit.subway.study.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.domain.Member;
import kuit.subway.utils.step.MemberStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@DisplayName("로그인 및 개인정보 조회 인수 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("로그인 인수 테스트")
    class Login {

        ExtractableResponse<Response> memberCreateRes;

        @BeforeEach
        void setUp() {
            memberCreateRes = MemberStep.회원_생성(20, "shin@gmail.com", "123");
        }

        @Nested
        @DisplayName("성공 케이스")
        class SuccessCase {

        }

        @Nested
        @DisplayName("실패 케이스")
        class FailCase {

        }
    }
}
