package kuit.subway.controller;

import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.request.station.CreateStationRequest;
import kuit.subway.dto.response.line.CreateLineResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.line.UpdateLineResponse;
import kuit.subway.dto.response.station.CreateStationResponse;
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
    public ResponseEntity<CreateLineResponse> saveLine(@RequestBody CreateLineRequest request) {

        CreateLineResponse res = lineService.addLine(request);
        return ResponseEntity.created(URI.create("/lines/" + res.getId()))
                .body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineDto> getLineById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lineService.findLineById(id));
    }

    @GetMapping()
    public ResponseEntity<List<LineDto>> getLines() {
        return ResponseEntity.ok(lineService.findAllLines());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateLineResponse> updateLine(@PathVariable("id") Long id, @RequestBody CreateLineRequest req) {
        return ResponseEntity.ok(lineService.updateLine(id, req));
    }
}
