package kuit.subway.service;

import kuit.subway.auth.JwtTokenProvider;
import kuit.subway.domain.Member;
import kuit.subway.dto.request.member.MemberRequest;
import kuit.subway.dto.response.member.MemberResponse;
import kuit.subway.exception.badrequest.member.DuplicateEmailException;
import kuit.subway.exception.notfound.member.NotFoundMemberException;
import kuit.subway.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse createMember(MemberRequest request) {

        // 중복되는 이메일이면 예외 발생
        validateSameEmail(request);

        Member member = memberRepository.save(request.toEntity());
        return MemberResponse.of(member);
    }

    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }

    public MemberResponse findMember(Long id) {
        Member member = validateMemberExist(id);
        return MemberResponse.of(member);
    }

    @Transactional
    public MemberResponse updateMember(Long id, MemberRequest memberRequest) {
        Member member = validateMemberExist(id);
        member.updateMember(memberRequest.toEntity());
        return new MemberResponse(id, memberRequest.getAge(), memberRequest.getEmail());
    }

    @Transactional
    public MemberResponse deleteMember(Long id) {
        Member member = validateMemberExist(id);
        memberRepository.delete(member);
        return MemberResponse.of(member);
    }

    private Member validateMemberExist(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException());
    }

    private void validateSameEmail(MemberRequest request) {
        memberRepository.findByEmail(request.getEmail())
                .ifPresent(findMember -> { throw new DuplicateEmailException(); });
    }

    public MemberResponse findMyInfo(Long memberId) {
        Optional<Member> findMemberOptional = memberRepository.findById(memberId);

        if (findMemberOptional.isEmpty()) {
            throw new NotFoundMemberException();
        }
        return MemberResponse.of(findMemberOptional.get());
    }
}
