package kuit.subway.controller;

import kuit.subway.domain.Member;
import kuit.subway.dto.request.auth.TokenRequest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.dto.response.member.MemberResponse;
import kuit.subway.service.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest request) {
        TokenResponse tokenResponse = authService.createToken(request);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
