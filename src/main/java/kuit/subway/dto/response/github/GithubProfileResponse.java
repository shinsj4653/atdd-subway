package kuit.subway.dto.response.github;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GithubProfileResponse {

    private String id;
    private String email;
    private String name;

}
