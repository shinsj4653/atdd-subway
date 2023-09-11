package kuit.subway.study.auth;

import kuit.subway.dto.request.github.GithubAccessTokenRequest;
import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.dto.response.github.GithubProfileResponse;
import kuit.subway.utils.fixture.GithubFixture;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class FakeGithubController {
    @PostMapping("/login/oauth/access_token")
    public ResponseEntity<GithubAccessTokenResponse> getAccessToken(
            @RequestBody GithubAccessTokenRequest tokenRequest) {
        try {
            return ResponseEntity.ok(GithubFixture.getAccessToken(tokenRequest.getCode()));
        } catch (IllegalStateException e) {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<GithubProfileResponse> getProfile(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken) {
        String token = accessToken.replaceAll("token ", "");
        try {
            return ResponseEntity.ok(GithubFixture.getGithubProfileByToken(token));
        } catch (IllegalStateException e) {
            return ResponseEntity.ok(null);
        }
    }

}
