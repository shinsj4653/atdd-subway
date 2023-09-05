package kuit.subway.dto.request.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenRequest {
    private String email;
    private String password;

    public TokenRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
