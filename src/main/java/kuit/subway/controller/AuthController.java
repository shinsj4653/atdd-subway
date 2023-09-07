package kuit.subway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kuit.subway.dto.request.auth.LoginRequest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.createToken(request);
        return ResponseEntity.created(URI.create("/token")).body(tokenResponse);
    }

    @GetMapping("/github/oauth")
    public ResponseEntity<TokenResponse> getGithubToken(@RequestParam("code") String code) throws IOException {
        TokenResponse tokenResponse = authService.createGithubToken(code);
        return ResponseEntity.created(URI.create("/github/oauth")).body(tokenResponse);
    }
//    public String access(String response, RedirectAttributes redirectAttributes) throws IOException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, String> map = objectMapper.readValue(response, Map.class);
//        String access_token = map.get("access_token");
//
//        URL url = new URL("https://api.github.com/user");
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
//        conn.setRequestProperty("Authorization", "token " + access_token);
//
//        int responseCode = conn.getResponseCode();
//
//        String result = getResponse(conn, responseCode);
//        System.out.println(result);
//
//        conn.disconnect();
//        redirectAttributes.addFlashAttribute("result", result);
//        return result;
//    }
//
}
