package kuit.subway.controller;

import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.line.PathReadRequest;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.dto.response.line.*;
import kuit.subway.dto.response.section.SectionCreateResponse;
import kuit.subway.dto.response.section.SectionDeleteResponse;
import kuit.subway.service.LineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lines")
public class LineController {

    private final LineService lineService;

    @PostMapping()
    public ResponseEntity<LineCreateResponse> saveLine(@RequestBody LineCreateRequest request) {

        LineCreateResponse res = lineService.addLine(request);
        return ResponseEntity.created(URI.create("/lines/" + res.getId()))
                .body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineReadResponse> getLineById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lineService.findLineById(id));
    }

    @GetMapping()
    public ResponseEntity<List<LineReadResponse>> getLines() {
        return ResponseEntity.ok(lineService.findAllLines());
    }


    @PutMapping("/{id}")
    public ResponseEntity<LineUpdateResponse> updateLine(@PathVariable("id") Long id, @RequestBody LineUpdateRequest req) {
        return ResponseEntity.ok(lineService.updateLine(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LineDeleteResponse> deleteLine(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lineService.deleteLine(id));
    }

    @PostMapping("/{id}/sections")
    public ResponseEntity<SectionCreateResponse> addSection(@PathVariable("id") Long lineId, @RequestBody SectionCreateRequest request) {

        SectionCreateResponse res = lineService.addSection(lineId, request);
        return ResponseEntity.created(URI.create("/sections/" + res.getId()))
                .body(res);
    }

    @DeleteMapping("/{id}/sections")
    public ResponseEntity<SectionDeleteResponse> deleteSection(@PathVariable("id") Long lineId, @RequestBody SectionDeleteRequest request) {

        SectionDeleteResponse res = lineService.deleteSection(lineId, request);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PathReadResponse> getPath(@PathVariable("id") Long id, @RequestBody PathReadRequest req) {
        return ResponseEntity.ok(lineService.findPath(id, req));
    }

}
