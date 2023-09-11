package kuit.subway.study.auth;

import kuit.subway.dto.response.github.GithubAccessTokenResponse;
import kuit.subway.study.config.HeaderConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "example", url = "http://localhost:9000" , configuration = {HeaderConfiguration.class})
public interface ExampleGithubClient {

    @GetMapping("/auth/login/github")
    GithubAccessTokenResponse githubTokenRequest(@RequestParam String code);
}
