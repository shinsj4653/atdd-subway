package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.station.StationCreateRequest;
import kuit.subway.dto.response.station.StationCreateResponse;
import kuit.subway.dto.response.station.StationDeleteResponse;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.exception.notfound.NotFoundException;
import kuit.subway.exception.notfound.NotFoundStationException;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public StationCreateResponse addStation(StationCreateRequest res) {
        Station station = Station.builder()
                        .name(res.getName()).build();
        stationRepository.save(station);

        return new StationCreateResponse("지하철 역 추가 완료", station.getId());
    }
    public List<StationDto> findStations() {
        List<Station> findStations = stationRepository.findAll();
        List<StationDto> result = findStations.stream()
                .map(station -> StationDto.builder()
                        .id(station.getId())
                        .name(station.getName())
                        .createdDate(station.getCreatedDate())
                        .modifiedDate(station.getModifiedDate()).build())
                .collect(Collectors.toList());
        return result;
    }

    public StationDto findStationById(Long id) {
        Station station = validateStationExist(id);

        StationDto result = StationDto.builder()
                .id(station.getId())
                .name(station.getName())
                .createdDate(station.getCreatedDate())
                .modifiedDate(station.getModifiedDate()).build();

        return result;
    }

    @Transactional
    public StationDeleteResponse deleteStation(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        stationRepository.delete(station);
        return new StationDeleteResponse("지하철 역 삭제 완료", station.getId());
    }

    // 존재하는 역인지 판별해주는 함수
    private Station validateStationExist(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(NotFoundStationException::new);
    }


}
