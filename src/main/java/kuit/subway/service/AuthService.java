package kuit.subway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kuit.subway.auth.JwtTokenProvider;
import kuit.subway.auth.github.GithubClient;
import kuit.subway.domain.Member;
import kuit.subway.dto.request.auth.LoginRequest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.dto.response.github.GithubProfileResponse;
import kuit.subway.exception.notfound.member.NotFoundMemberException;
import kuit.subway.exception.badrequest.auth.InvalidPasswordException;
import kuit.subway.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.*;
import java.util.Map;

import java.net.HttpURLConnection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final GithubClient githubClient;


    public TokenResponse createToken(LoginRequest loginRequest) {
        Member member = findMember(loginRequest);
        if (member.isInvalidPassword(loginRequest.getPassword())) {
            throw new InvalidPasswordException();
        }
        String accessToken = jwtTokenProvider.createToken(member.getId());
        return TokenResponse.of(accessToken);
    }

    public TokenResponse createTokenFromGithub(String code) {
        String accessTokenFromGithub = githubClient.getAccessTokenFromGithub(code);
        Map<Object, Object> githubProfile = githubClient.getGithubProfileFromGithub(accessTokenFromGithub);

        String token = jwtTokenProvider.createToken(String.valueOf(githubProfile.get("email")));
        return TokenResponse.of(token);
    }

    public TokenResponse createGithubToken(String code) throws IOException {
        URL url = new URL("https://github.com/login/oauth/access_token");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");

        // 이 부분에 client_id, client_secret, code를 넣어주기
        // client_secret 값은 주의하도록
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
            bw.write("client_id=67bb75be8f468a39c2d1&client_secret=d9485c513f8595d1dd9c499eca6aa379221f9ad5&code=" + code);
            bw.flush();
        }

        int responseCode = conn.getResponseCode();

        String responseData = getResponse(conn, responseCode);
        System.out.println(responseData);

        conn.disconnect();

        // Map<String, String> 자료구조 타입으로 반환
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = objectMapper.readValue(responseData, Map.class);

        // access_token 값 추출 후, TokenResponse로 가져오기
        return TokenResponse.of(result.get("access_token"));
    }

    //    public String access(String get-token-response.json, RedirectAttributes redirectAttributes) throws IOException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, String> map = objectMapper.readValue(get-token-response.json, Map.class);
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

    private Member findMember(LoginRequest loginRequest) {
        return memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundMemberException());
    }

    private String getResponse(HttpURLConnection conn, int responseCode) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (responseCode == 200) {
            try (InputStream is = conn.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    sb.append(line);
                }
            }
        }
        return sb.toString();
    }

}
