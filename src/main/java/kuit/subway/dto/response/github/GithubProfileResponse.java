package kuit.subway.dto.response.github;

import kuit.subway.dto.request.auth.OAuth2UserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GithubProfileResponse implements OAuth2UserRequest {

    private String email;
    private Integer age;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Integer getAge() {
        return age;
    }
}
