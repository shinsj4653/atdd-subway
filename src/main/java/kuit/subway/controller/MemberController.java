package kuit.subway.controller;

import kuit.subway.auth.LoginUserId;
import kuit.subway.domain.Member;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.member.MemberRequest;
import kuit.subway.dto.response.line.LineCreateResponse;
import kuit.subway.dto.response.line.LineDeleteResponse;
import kuit.subway.dto.response.line.LineReadResponse;
import kuit.subway.dto.response.member.MemberResponse;
import kuit.subway.service.LineService;
import kuit.subway.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request) {
        MemberResponse res = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/members/" + res.getId()))
                .body(res);
    }
    @GetMapping()
    public ResponseEntity<List<MemberResponse>> findAllMembers() {
        return ResponseEntity.ok(memberService.findAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable("id") Long memberId) {
        return ResponseEntity.ok(memberService.findMember(memberId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable("id") Long memberId, @RequestBody MemberRequest req) {
        return ResponseEntity.ok(memberService.updateMember(memberId, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable("id") Long memberId) {
        return ResponseEntity.ok(memberService.deleteMember(memberId));
    }
    @GetMapping("/myinfo")
    public ResponseEntity<MemberResponse> findMyMemberInfo(@LoginUserId Long memberId) {
        MemberResponse response = memberService.findMyInfo(memberId);
        return ResponseEntity.ok(response);
    }
}
