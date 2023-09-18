package kuit.subway.controller;

import jakarta.validation.Valid;
import kuit.subway.dto.request.auth.LoginRequest;
import kuit.subway.dto.request.github.GithubTokenRequest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.createToken(request);
        return ResponseEntity.created(URI.create("/login/token")).body(tokenResponse);
    }

    @GetMapping("/login/github")
    public ResponseEntity<TokenResponse> loginWithGithub(@RequestParam String code) {
        TokenResponse tokenResponse = authService.createTokenFromGithub(code);
        return ResponseEntity.created(URI.create("/login/github")).body(tokenResponse);
    }

}
