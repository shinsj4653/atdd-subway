//package kuit.subway.study.auth;
//
//import com.google.common.util.concurrent.Service;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import kuit.subway.AcceptanceTest;
//import kuit.subway.dto.response.auth.TokenResponse;
//import kuit.subway.service.AuthService;
//import kuit.subway.study.common.CommonRestAssured;
//import org.junit.jupiter.api.*;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//
//import java.io.IOException;
//
//import static kuit.subway.utils.step.AuthStep.깃허브_로그인_요청;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@AutoConfigureWireMock(port = 0)
//@DisplayName("Github OAuth 로그인 인수테스트")
//public class GithubOAuthAcceptanceTest extends AcceptanceTest {
//
//
//    //    private final String PATH = "https://github.com/login/oauth/access_token?client_id=client_id&client_secret=client_secret&code=code";
//    @Autowired
//    private AuthService authService;
//    private final String START_GITHUB_LOGIN_PATH = "https://github.com/login/oauth/authorize?client_id=67bb75be8f468a39c2d1&redirect_uri=http://localhost:8080/auth/login/github";
//
//    @Nested
//    @DisplayName("Github 로그인 인수 테스트")
//    class GithubLogin {
//
//        @BeforeEach
//        void setUp() throws IOException {
//            OAuthMocks.setUpResponses();
//        }
//
//        @Nested
//        @DisplayName("성공 케이스")
//        class SuccessCase {
//
//            @Test
//            @DisplayName("Github OAuth 로그인 성공")
//            void githubLoginSuccess() throws IOException {
//
//                // given
//                ExtractableResponse<Response> loginRes = CommonRestAssured.startGithubLogin(START_GITHUB_LOGIN_PATH);
//                System.out.println(loginRes.response().toString());
//                TokenResponse code = authService.createGithubToken("code");
//
//                // when
//                // then
//                assertThat(code.getAccessToken()).isNotNull();
//            }
//
//        }
//
//    }
//}
