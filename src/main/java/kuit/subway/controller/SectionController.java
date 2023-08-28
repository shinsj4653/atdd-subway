package kuit.subway.controller;

import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.dto.response.line.LineReadResponse;
import kuit.subway.dto.response.section.SectionCreateResponse;
import kuit.subway.dto.response.section.SectionDeleteResponse;
import kuit.subway.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    @PostMapping("/{id}")
    public ResponseEntity<SectionCreateResponse> addSection(@PathVariable("id") Long lineId, @RequestBody SectionCreateRequest request) {

        SectionCreateResponse res = sectionService.addSection(lineId, request);
        return ResponseEntity.created(URI.create("/sections/" + res.getId()))
                .body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SectionDeleteResponse> deleteSection(@PathVariable("id") Long lineId, @RequestBody SectionDeleteRequest request) {

        SectionDeleteResponse res = sectionService.deleteSection(lineId, request);
        return ResponseEntity.ok().body(res);
    }
}
