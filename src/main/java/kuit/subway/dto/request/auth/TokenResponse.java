package kuit.subway.dto.request.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {
    private String accessToken;

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
