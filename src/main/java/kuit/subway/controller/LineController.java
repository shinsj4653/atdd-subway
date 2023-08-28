package kuit.subway.controller;

import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.line.PathFindRequest;
import kuit.subway.dto.response.line.*;
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

    @PostMapping("/{id}")
    public ResponseEntity<PathReadResponse> getPath(@PathVariable("id") Long id, @RequestBody PathFindRequest req) {
        return ResponseEntity.ok(lineService.findPath(id, req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineUpdateResponse> updateLine(@PathVariable("id") Long id, @RequestBody LineUpdateRequest req) {
        return ResponseEntity.ok(lineService.updateLine(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LineDeleteResponse> deleteLine(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lineService.deleteLine(id));
    }
}
