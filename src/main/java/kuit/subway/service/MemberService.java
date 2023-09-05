package kuit.subway.service;

import kuit.subway.domain.Member;
import kuit.subway.dto.request.member.MemberRequest;
import kuit.subway.dto.response.member.MemberResponse;
import kuit.subway.exception.notfound.member.NotFoundMemberException;
import kuit.subway.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse createMember(MemberRequest request) {
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        Member member = validateMemberExist(id);
        return MemberResponse.of(member);
    }

    public MemberResponse updateMember(Long id, MemberRequest memberRequest) {
        Member member = validateMemberExist(id);
        member.updateMember(memberRequest.toMember());
        return new MemberResponse(id, memberRequest.getAge(), memberRequest.getEmail());
    }

    public MemberResponse deleteMember(Long id) {
        Member member = validateMemberExist(id);
        memberRepository.delete(member);
        return MemberResponse.of(member);
    }

    private Member validateMemberExist(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException());
    }
}
