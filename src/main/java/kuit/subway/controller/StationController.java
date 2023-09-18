package kuit.subway.controller;

import kuit.subway.dto.request.station.StationCreateRequest;
import kuit.subway.dto.response.station.StationCreateResponse;
import kuit.subway.dto.response.station.StationDeleteResponse;
import kuit.subway.dto.response.station.StationReadResponse;
import kuit.subway.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
public class StationController {

    private final StationService stationService;

    @GetMapping()
    public ResponseEntity<List<StationReadResponse>> getStations() {
        return ResponseEntity.ok(stationService.findStations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationReadResponse> getStationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(stationService.findStationById(id));
    }

    @PostMapping()
    public ResponseEntity<StationCreateResponse> saveStation(@RequestBody StationCreateRequest request) {

        StationCreateResponse res = stationService.addStation(request);
        return ResponseEntity.created(URI.create("/stations/" + res.getId()))
                            .body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StationDeleteResponse> deleteStation(@PathVariable("id") Long id) {
        StationDeleteResponse res = stationService.deleteStation(id);
        return ResponseEntity.ok(res);
    }

}
