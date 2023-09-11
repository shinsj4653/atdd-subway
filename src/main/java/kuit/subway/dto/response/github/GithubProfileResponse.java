package kuit.subway.dto.response.github;

import kuit.subway.dto.request.auth.OAuth2UserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GithubProfileResponse implements OAuth2UserRequest {

    private String email;
    private String name;
    private Integer age;

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    public GithubProfileResponse(String name, int age) {
        this.name = name;
        this.age = age;
    }


}
