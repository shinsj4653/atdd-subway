package kuit.subway.service;

import kuit.subway.domain.Station;
import kuit.subway.dto.request.station.CreateStationRequest;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.dto.response.station.DeleteStationResponse;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public CreateStationResponse addStation(CreateStationRequest res) {
        Station station = new Station(res.getName());
        stationRepository.save(station);
        return new CreateStationResponse(station.getId());
    }

    @Transactional
    public List<StationDto> findStations() {
        List<Station> findStations = stationRepository.findAll();
        List<StationDto> result = findStations.stream()
                .map(m -> new StationDto(m.getId(), m.getName()))
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public DeleteStationResponse deleteStation(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        stationRepository.delete(station);
        return new DeleteStationResponse(station.getId());
    }
}
