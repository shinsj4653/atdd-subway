package kuit.subway.utils.fixture;

import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.dto.response.github.GithubProfileResponse;

import java.util.Arrays;

public enum GithubFixture {
    주디("client_id_1", "client_secret_1", "1", "token1", "주디", "jurlring", "one@gmail.com", 20),
    슬로("client_id_2", "client_secret_2", "2", "token2", "슬로", "hanull", "two@gmail.com", 21),
    레넌("client_id_3", "client_secret_3", "3", "token3", "레넌", "brorae", "three@gmail.com", 22),
    기론("client_id_4", "client_secret_4", "4", "token4", "기론", "gyuchool", "four@gmail.com", 25);

    private final String clientId;

    private final String clientSecret;
    private final String code;
    private final String token;
    private final String name;
    private final String githubId;
    private final String email;

    private final int age;

    GithubFixture(String clientId, String clientSecret, String code, String token, String name, String githubId, String email, int age) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.token = token;
        this.name = name;
        this.githubId = githubId;
        this.email = email;
        this.age = age;
    }
    public static GithubAccessTokenResponse getAccessToken(String code) {
        GithubFixture client = Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("로그인이 실패했습니다."));
        return new GithubAccessTokenResponse(client.token);
    }

    public static GithubProfileResponse getGithubProfileByToken(String token) {
        GithubFixture client = Arrays.stream(values())
                .filter(value -> value.token.equals(token))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("로그인이 실패했습니다."));
        return new GithubProfileResponse(client.email, client.age);
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getGithubId() {
        return githubId;
    }

}
