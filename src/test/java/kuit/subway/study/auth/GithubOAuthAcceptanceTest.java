package kuit.subway.study.auth;

import com.google.common.util.concurrent.Service;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.service.AuthService;
import kuit.subway.study.common.CommonRestAssured;
import kuit.subway.utils.step.AuthStep;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.io.IOException;

import static kuit.subway.utils.fixture.GithubFixture.주디;
import static kuit.subway.utils.step.AuthStep.깃허브_로그인_요청;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Github OAuth 로그인 인수테스트")
public class GithubOAuthAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("Github 로그인 인수 테스트")
    class GithubLogin {

        @Nested
        @DisplayName("성공 케이스")
        class SuccessCase {

            @Test
            @DisplayName("Github OAuth 로그인 성공")
            void githubLoginSuccess() {

                // given
                GithubAccessTokenResponse 깃허브_로그인_요청_결과 = 깃허브_로그인_요청(주디);

                // when
                // then
                assertThat(깃허브_로그인_요청_결과.getAccessToken()).isNotNull();
            }

        }

    }
}
