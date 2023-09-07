package kuit.subway.study.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.io.IOException;

import static kuit.subway.utils.step.AuthStep.깃허브_로그인_요청;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWireMock(port = 0)
@DisplayName("Github OAuth 로그인 인수테스트")
public class GithubOAuthAcceptanceTest extends AcceptanceTest {


    private final String PATH = "https://github.com/login/oauth/access_token?client_id=67bb75be8f468a39c2d1&client_secret=d9485c513f8595d1dd9c499eca6aa379221f9ad5&code=code";

    @Nested
    @DisplayName("Github 로그인 인수 테스트")
    class GithubLogin {

        @BeforeEach
        void setUp() throws IOException {
            OAuthMocks.setUpResponses();
        }

        @Nested
        @DisplayName("성공 케이스")
        class SuccessCase {

            @Test
            @DisplayName("Github OAuth 로그인 성공")
            void githubLoginSuccess() {

                // given
                ExtractableResponse<Response> 깃허브_로그인_요청_응답 = 깃허브_로그인_요청(PATH);

                // when
                // then
                assertThat(깃허브_로그인_요청_응답.jsonPath().getString("access_token")).isNotNull();
            }

        }

    }
}
