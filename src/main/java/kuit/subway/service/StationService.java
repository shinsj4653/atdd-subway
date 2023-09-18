package kuit.subway.service;

import kuit.subway.domain.Station;
import kuit.subway.dto.request.station.StationCreateRequest;
import kuit.subway.dto.response.station.StationCreateResponse;
import kuit.subway.dto.response.station.StationDeleteResponse;
import kuit.subway.dto.response.station.StationReadResponse;
import kuit.subway.exception.notfound.station.NotFoundStationException;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public StationCreateResponse addStation(StationCreateRequest res) {
        Station station = Station.createStation(res.getName());
        stationRepository.save(station);
        return StationCreateResponse.of(station);
    }

    public List<StationReadResponse> findStations() {
        List<Station> findStations = stationRepository.findAll();
        return findStations.stream()
                .map(station -> StationReadResponse.of(station))
                .collect(Collectors.toList());

    }

    public StationReadResponse findStationById(Long id) {
        Station station = validateStationExist(id);
        return StationReadResponse.of(station);
    }

    @Transactional
    public StationDeleteResponse deleteStation(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(NotFoundStationException::new);

        stationRepository.delete(station);
        return StationDeleteResponse.of(station);
    }

    // 존재하는 역인지 판별해주는 함수
    private Station validateStationExist(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(NotFoundStationException::new);
    }

}
