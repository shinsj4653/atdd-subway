package kuit.subway.controller;

import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.request.station.CreateStationRequest;
import kuit.subway.dto.response.line.CreateLineResponse;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.service.LineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lines")
public class LineController {

    private final LineService lineService;

    @PostMapping()
    public ResponseEntity<CreateLineResponse> saveStation(@RequestBody CreateLineRequest request) {

        CreateLineResponse res = lineService.addLine(request);
        return ResponseEntity.created(URI.create("/lines/" + res.getId()))
                .body(res);
    }
}
