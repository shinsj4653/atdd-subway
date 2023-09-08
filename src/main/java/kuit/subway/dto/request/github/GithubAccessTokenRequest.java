package kuit.subway.dto.request.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GithubAccessTokenRequest {
    private String client_id;
    private String client_secret;
    private String code;
}
