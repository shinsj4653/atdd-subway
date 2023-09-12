package kuit.subway.utils.step;

import io.restassured.RestAssured;
import kuit.subway.dto.request.github.GithubAccessTokenRequest;
import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.dto.response.github.GithubProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class GithubStep {

    public static GithubAccessTokenResponse 깃허브_액세스_토큰_요청() {

        GithubAccessTokenRequest request = new GithubAccessTokenRequest("id", "secret", "code");
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/login/oauth/access_token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(GithubAccessTokenResponse.class);
    }

    public static GithubProfileResponse 깃허브_프로필_요청(String accessToken) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when()
                .get("/user")
                .then().log().all()
                .extract()
                .as(GithubProfileResponse.class);
    }

}
