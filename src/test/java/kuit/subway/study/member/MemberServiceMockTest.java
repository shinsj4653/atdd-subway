package kuit.subway.study.member;

import kuit.subway.auth.JwtTokenProvider;
import kuit.subway.domain.Member;
import kuit.subway.dto.request.auth.LoginRequest;
import kuit.subway.dto.response.auth.TokenResponse;
import kuit.subway.dto.response.member.MemberResponse;
import kuit.subway.repository.MemberRepository;
import kuit.subway.service.AuthService;
import kuit.subway.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("로그인 후 회원 정보 조회 Mock 테스트")
public class MemberServiceMockTest {

    public static final String EMAIL = "shin@email.com";
    public static final String PASSWORD = "1234";
    public static final int AGE = 20;

    public static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjk0MDQ4Njc1LCJleHAiOjE2OTQwNTIyNzV9.3VKZXLJvnTaEk5Kf5Mko2tIkrd_ArocbH_hKhIN_V2Q";

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("토큰 생성 Mock 테스트")
    class CreateToken {

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("로그인 시 생성된 토큰을 이용하여 내 정보 조회")
            void createTokenSuccess() {

                // given
                Member member = new Member(1L, AGE, EMAIL, PASSWORD);
                given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

                // when
                MemberResponse myInfo = memberService.findMyInfo(member.getId());

                // then
                assertThat(myInfo.getId()).isEqualTo(member.getId());
            }
        }

    }

//    @Nested
//    @DisplayName("로그인 후, 회원 정보 조회 Mock 테스트")
//    class GetMyMemberInfo {
//
//        @Nested
//        @DisplayName("정상 케이스")
//        class SuccessCase {
//            @Test
//            @DisplayName("로그인 시 생성된 토큰을 이용하여 내 정보 조회")
//            void getMyMemberInfoSuccess() {
//
//                // given
//                Member member = new Member(AGE, EMAIL, PASSWORD);
//                given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
//                given(jwtTokenProvider.createToken(member.getId())).willReturn("TOKEN");
//
//                // when
//                MemberResponse findMember = authService.findLoginMemberByToken("TOKEN");
//
//                // then
//                assertAll(() -> {
//                    assertThat(findMember.getEmail()).isEqualTo(EMAIL);
//                    assertThat(findMember.getEmail()).isEqualTo(EMAIL);
//                });
//
//            }
//        }
//
//    }


}
