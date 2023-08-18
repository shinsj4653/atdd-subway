package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.station.StationCreateRequest;
import kuit.subway.dto.response.station.StationCreateResponse;
import kuit.subway.dto.response.station.StationDeleteResponse;
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
    public StationCreateResponse addStation(StationCreateRequest res) {
        Station station = new Station(res.getName());
        stationRepository.save(station);
        return new StationCreateResponse(station.getId());
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
    public StationDto findStationById(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        StationDto result = StationDto.builder()
                .id(id)
                .name(station.getName()).build();

        return result;
    }

    @Transactional
    public StationDeleteResponse deleteStation(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        stationRepository.delete(station);
        return new StationDeleteResponse(station.getId());
    }
}
