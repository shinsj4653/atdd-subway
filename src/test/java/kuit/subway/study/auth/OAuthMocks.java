package kuit.subway.study.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class OAuthMocks {
    public static void setUpResponses() throws IOException {
        setupMockTokenResponse();
    }

    public static void setupMockTokenResponse() throws IOException {
        String body = "{\"accessToken\":\"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\"}";
//        client_id=67bb75be8f468a39c2d1&client_secret=d9485c513f8595d1dd9c499eca6aa379221f9ad5
        stubFor(post(urlEqualTo("https://github.com/login/oauth/access_token?client_id=client_id&client_secret=client_secret&code=code"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(body)
                )
        );

    }

//    public static String getMockResponseBodyByPath(String filePath) throws IOException {
//        return new String(Files.readAllBytes(Paths.get(filePath)));
//    }

}
