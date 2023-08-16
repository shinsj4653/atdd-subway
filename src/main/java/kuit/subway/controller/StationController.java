package kuit.subway.controller;

import kuit.subway.domain.Station;
import kuit.subway.request.CreateStationRequest;
import kuit.subway.response.CreateStationResponse;
import kuit.subway.service.StationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping("/stations")
    public List<Station> getStations() {
        return stationService.findStations();
    }

    @PostMapping("/stations")
    public CreateStationResponse saveStation(@RequestBody CreateStationRequest request) {

        Station station = new Station();
        station.setName(request.getName());

        Long id = stationService.addStation(station);
        return new CreateStationResponse(id);
    }

    @DeleteMapping("/stations/{id}")
    public void deleteStation(@PathVariable("id") Long id) {
        stationService.deleteStation(id);
    }




}
