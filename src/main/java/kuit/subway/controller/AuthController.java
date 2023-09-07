package kuit.subway.controller;

import kuit.subway.dto.request.auth.LoginRequest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.createToken(request);
        return ResponseEntity.created(URI.create("/token")).body(tokenResponse);
    }

}
