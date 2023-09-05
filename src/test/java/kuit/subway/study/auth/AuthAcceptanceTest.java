package kuit.subway.study.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.domain.Member;
import kuit.subway.utils.step.AuthStep;
import kuit.subway.utils.step.MemberStep;
import org.junit.jupiter.api.*;

import static kuit.subway.utils.step.AuthStep.로그인_회원_토근_생성;
import static kuit.subway.utils.step.MemberStep.회원_생성;

@DisplayName("로그인 및 개인정보 조회 인수 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("로그인 인수 테스트")
    class Login {

        ExtractableResponse<Response> memberCreateRes;

        @BeforeEach
        void setUp() {
            memberCreateRes = 회원_생성(20, "shin@gmail.com", "123");
        }

        @Nested
        @DisplayName("성공 케이스")
        class SuccessCase {

            @Test
            @DisplayName("아이디와 패스워드를 이용하여 토큰 생성")
            void createToken() {

                // given
                String email = memberCreateRes.jsonPath().getString("email");
                String password = "123";

                // when
                ExtractableResponse<Response> tokenRes = 로그인_회원_토근_생성(email, password);

                // then
                Assertions.assertEquals(201, tokenRes.statusCode());
            }

        }

        @Nested
        @DisplayName("실패 케이스")
        class FailCase {

        }
    }
}
