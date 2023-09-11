package kuit.subway.study.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "example", url = "http://localhost:9000")
public interface ExampleGithubClient {

    @GetMapping("/auth/login/github")
    Object request(@RequestParam String code);
}
