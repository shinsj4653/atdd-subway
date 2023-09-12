package kuit.subway.study.auth;

import kuit.subway.AcceptanceTest;
import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Github OAuth 로그인 인수테스트")
public class GithubOAuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private ExampleGithubClient exampleGithubClient;
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
                GithubAccessTokenResponse 깃허브_로그인_요청_결과 = exampleGithubClient.githubTokenRequest("code1");

                // when
                // then
                assertThat(깃허브_로그인_요청_결과.getAccessToken()).isNotNull();
            }

        }

    }
}
