package kuit.subway.controller;

import kuit.subway.domain.Station;
import kuit.subway.dto.request.CreateStationRequest;
import kuit.subway.dto.response.CreateStationResponse;
import kuit.subway.dto.response.StationDto;
import kuit.subway.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping("/stations")
    public ResponseEntity<List<StationDto>> getStations() {
        return ResponseEntity.ok(stationService.findStations());
    }

    @PostMapping("/stations")
    public ResponseEntity<CreateStationResponse> saveStation(@RequestBody CreateStationRequest request) {

        Station station = new Station();
        station.setName(request.getName());

        CreateStationResponse res = stationService.addStation(station);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/stations/{id}")
    public void deleteStation(@PathVariable("id") Long id) {
        stationService.deleteStation(id);
    }


}
