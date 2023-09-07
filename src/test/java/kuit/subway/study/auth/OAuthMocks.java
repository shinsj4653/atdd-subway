package kuit.subway.study.auth;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static kuit.subway.study.common.CommonRestAssured.post;

public class OAuthMocks {
    public static void setUpResponses() throws IOException {
        setupMockTokenResponse();
    }

    public static void setupMockTokenResponse() throws IOException {
        stubFor(get(urlEqualTo("/oauth/access_token?grant_type=authorization_code&client_id=67bb75be8f468a39c2d1&&code=code"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getMockResponseBodyByPath("payload/get-token-response.json"))
                )
        );
    }

}
