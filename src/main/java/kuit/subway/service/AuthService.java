package kuit.subway.service;

import kuit.subway.auth.JwtTokenProvider;
import kuit.subway.domain.Member;
import kuit.subway.dto.request.auth.TokenRequest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.exception.notfound.member.NotFoundMemberException;
import kuit.subway.exception.badrequest.auth.InvalidPasswordException;
import kuit.subway.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
