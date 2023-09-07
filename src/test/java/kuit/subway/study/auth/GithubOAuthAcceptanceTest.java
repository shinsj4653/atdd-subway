package kuit.subway.study.auth;

import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@AutoConfigureWireMock(port = 0)
@DisplayName("Github OAuth 로그인 인수테스트")
public class GithubOAuthAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("Github 로그인 인수 테스트")
    class GithubLogin {

    }
}
