package kuit.subway.study.auth;

import kuit.subway.AcceptanceTest;
import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.dto.response.github.GithubProfileResponse;
import kuit.subway.utils.step.GithubStep;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static kuit.subway.utils.step.GithubStep.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DisplayName("Github OAuth 로그인 인수테스트")
public class GithubOAuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    FakeGithubClient githubClient;

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
                //GithubAccessTokenResponse 깃허브_토큰_요청_결과 = 깃허브_액세스_토큰_요청();
                //GithubProfileResponse 깃허브_프로필_요청_결과 = 깃허브_프로필_요청(깃허브_토큰_요청_결과.getAccessToken());

                GithubAccessTokenResponse 깃허브_토큰_요청_결과 = githubClient.getAccessTokenFromGithub("accessCode");
                GithubProfileResponse 깃허브_프로필_요청_결과 = githubClient.getGithubProfileFromGithub(깃허브_토큰_요청_결과.getAccessToken());

                // when
                // then
                assertAll(() -> {
                    assertThat(깃허브_토큰_요청_결과.getAccessToken()).isNotNull();
                    assertThat(깃허브_프로필_요청_결과.getName()).isEqualTo("shin");
                });
            }

        }

    }
}
