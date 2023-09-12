package kuit.subway.study.auth;

import kuit.subway.dto.request.github.GithubAccessTokenRequest;
import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.dto.response.github.GithubProfileResponse;
import kuit.subway.utils.fixture.GithubFixture;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MockGithubServerController {
    @PostMapping(path = "/login/oauth/access_token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<GithubAccessTokenResponse> getAccessToken(
            HttpEntity<GithubAccessTokenRequest> request) {

        GithubAccessTokenResponse response = new GithubAccessTokenResponse("token1");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/user" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<GithubProfileResponse> getGithubProfile(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken) {

        Map<Object, Object> response = new HashMap<>();
        response.put("id", 54321L);

        return ResponseEntity.ok(response);
    }

}
