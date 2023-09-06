package kuit.subway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import kuit.subway.auth.JwtTokenProvider;
import kuit.subway.domain.Member;
import kuit.subway.dto.request.auth.TokenRequest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.dto.response.member.MemberResponse;
import kuit.subway.exception.notfound.member.NotFoundMemberException;
import kuit.subway.exception.unauthorized.InvalidPasswordException;
import kuit.subway.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = findMember(tokenRequest);
        if (member.isInvalidPassword(tokenRequest.getPassword())) {
            throw new InvalidPasswordException();
        }
        String accessToken = jwtTokenProvider.createToken(member.getId());
        return new TokenResponse(accessToken);
    }


    private Member findMember(TokenRequest tokenRequest) {
        return memberRepository.findByEmail(tokenRequest.getEmail())
                .orElseThrow(() -> new NotFoundMemberException());
    }

    public MemberResponse findLoginMemberByToken(String token) {
        Long id = Long.valueOf(jwtTokenProvider.getPayload(token));
        Optional<Member> findMemberOptional = memberRepository.findById(id);

        if (findMemberOptional.isEmpty()) {
            throw new NotFoundMemberException();
        }
        return MemberResponse.of(findMemberOptional.get());
    }

}
