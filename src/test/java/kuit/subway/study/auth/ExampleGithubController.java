package kuit.subway.study.auth;

import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.dto.response.github.GithubProfileResponse;
import kuit.subway.utils.fixture.GithubFixture;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExampleGithubController {
    @GetMapping("/auth/login/github")
    public ResponseEntity<GithubAccessTokenResponse> getAccessToken(
            @RequestParam String code) {

        if (!code.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        GithubAccessTokenResponse response = new GithubAccessTokenResponse("token1");
        return new ResponseEntity<>(response, HttpStatus.OK);
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
