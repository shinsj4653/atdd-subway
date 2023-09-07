package kuit.subway.study.member;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.response.auth.TokenResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static kuit.subway.utils.step.AuthStep.로그인_회원_토근_생성;
import static kuit.subway.utils.step.MemberStep.내_회원_정보_요청;
import static kuit.subway.utils.step.MemberStep.회원_생성;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("회원가입 인수 테스트")
    class SignUp {

        @Nested
        @DisplayName("회원가입 성공")
        class SuccessCase {
            @Test
            @DisplayName("회원가입을 한다.")
            void signUpSuccess(){

                // given
                // when
                ExtractableResponse<Response> 회원_생성_결과 = 회원_생성(20, "shin@gmail.com", "12345678!");

                // then
                assertEquals(HttpStatus.CREATED.value(), 회원_생성_결과.statusCode());
            }
        }

        @Nested
        @DisplayName("회원가입 실패")
        class FailCase {
            @Test
            @DisplayName("회원가입시 검증조건을 만족하지 못하는 경우 예외가 발생한다.")
            void signUpFail(){
                // when
                ExtractableResponse<Response> 회원_생성_결과 = 회원_생성(-1, "test", "12345678");

                // then
                assertEquals(HttpStatus.BAD_REQUEST.value(), 회원_생성_결과.statusCode());
            }
        }

    }

    @Nested
    @DisplayName("회원 정보 조회 인수 테스트")
    class GetMyInfo {

        String token;
        @BeforeEach
        void setUp() {
            회원_생성(20, "shin@gmail.com", "123");
            ExtractableResponse<Response> tokenRes = 로그인_회원_토근_생성("shin@gmail.com", "123");
            token = tokenRes.as(TokenResponse.class).getAccessToken();
        }

        @Nested
        @DisplayName("정보 조회 성공")
        class SuccessCase {

            @Test
            @DisplayName("로그인 시 생성된 토큰을 이용하여 내 정보 조회")
            void getMyInfoSuccess() {

                // given
                ExtractableResponse<Response> 내_회원_정보_요청_결과 = 내_회원_정보_요청(token);

                // when
                // then
                assertAll(() -> {
                    assertEquals(200, 내_회원_정보_요청_결과.statusCode());
                    assertEquals(20, 내_회원_정보_요청_결과.jsonPath().getInt("age"));
                    assertEquals("shin@gmail.com", 내_회원_정보_요청_결과.jsonPath().getString("email"));
                });
            }
        }

    }
}
