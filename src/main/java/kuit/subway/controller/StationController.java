package kuit.subway.controller;

import kuit.subway.dto.request.station.CreateStationRequest;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.dto.response.station.DeleteStationResponse;
import kuit.subway.dto.response.station.StationDto;
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
    public ResponseEntity<List<StationDto>> getStations() {
        return ResponseEntity.ok(stationService.findStations());
    }

    @PostMapping()
    public ResponseEntity<CreateStationResponse> saveStation(@RequestBody CreateStationRequest request) {

        CreateStationResponse res = stationService.addStation(request);
        return ResponseEntity.created(URI.create("/stations/" + res.getId()))
                            .body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteStationResponse> deleteStation(@PathVariable("id") Long id) {
        DeleteStationResponse res = stationService.deleteStation(id);
        return ResponseEntity.ok(res);
    }

}
