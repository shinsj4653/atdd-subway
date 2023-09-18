package kuit.subway.dto.response.auth;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;

    public static TokenResponse of(String token){
        return TokenResponse.builder()
                .accessToken(token)
                .build();
    }
}
