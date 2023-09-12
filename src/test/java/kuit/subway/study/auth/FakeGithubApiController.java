package kuit.subway.study.auth;

import kuit.subway.dto.request.github.GithubAccessTokenRequest;
import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.dto.response.github.GithubProfileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class FakeGithubApiController {

    private String clientId;
    private String clientSecret;

    public FakeGithubApiController(@Value("${github.client_id}") String clientId, @Value("${github.client_secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @PostMapping(path = "/login/oauth/access_token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<GithubAccessTokenResponse> getFakeAccessToken(
            @RequestBody final GithubAccessTokenRequest request) {

        if (!request.getClient_id().equals(clientId) || !request.getClient_secret().equals(clientSecret)) {
            return ResponseEntity.badRequest().build();
        }

        GithubAccessTokenResponse response = new GithubAccessTokenResponse("accessToken");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/user" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<GithubProfileResponse> getFakeGithubProfile(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeaderValue) {

        if (authorizationHeaderValue == null) {
            return ResponseEntity.badRequest().build();
        }
        final String[] splitValue = authorizationHeaderValue.split(" ");
        if (splitValue.length != 2 || !splitValue[0].equals("token")) {
            return ResponseEntity.badRequest().build();
        }

        GithubProfileResponse response = new GithubProfileResponse("1234", "shin@gmail.com", "싱준");
        return ResponseEntity.ok(response);
    }

}
