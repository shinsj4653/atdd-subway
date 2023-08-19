package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.station.CreateStationRequest;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.dto.response.station.DeleteStationResponse;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public CreateStationResponse addStation(CreateStationRequest res) {
        Station station = Station.builder()
                        .name(res.getName()).build();
        stationRepository.save(station);

        return new CreateStationResponse("지하철 역 추가 완료", station.getId());
    }
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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
    public DeleteStationResponse deleteStation(Long id) {
        Station station = validateStationExist(id);

        stationRepository.delete(station);
        return new DeleteStationResponse("지하철 역 삭제 완료", station.getId());
    }

    // 존재하는 역인지 판별해주는 함수
    private Station validateStationExist(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(NotFoundStationException::new);
    }
}
