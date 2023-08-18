package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Line;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.response.line.CreateLineResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.line.UpdateLineResponse;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional
    public CreateLineResponse addLine(CreateLineRequest res) {

        // 존재하지 않는 station_id를 추가하려고 하면 예외발생
        stationRepository.findById(res.getDownStationId()).orElseThrow(EntityNotFoundException::new);
        stationRepository.findById(res.getUpStationId()).orElseThrow(EntityNotFoundException::new);

        // 만약, 둘 다 존재하는 역이라면 노선 생성
        Line line = new Line(res.getColor(), res.getDistance(), res.getName(), res.getDownStationId(), res.getUpStationId());
        lineRepository.save(line);

        return new CreateLineResponse(line.getId());
    }

    @Transactional
    public LineDto findLineById(Long id) {

        // 존재하지 않는 노선을 조회했을 때 예외처리
        Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        LineDto result = LineDto.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .stations(createStationList(line.getDownStationId(), line.getUpStationId()))
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        return result;
    }

    @Transactional
    public List<LineDto> findAllLines() {

        List<Line> findLines = lineRepository.findAll();
        List<LineDto> result = findLines.stream()
                .map(line -> LineDto.builder()
                        .id(line.getId())
                        .name(line.getName())
                        .color(line.getColor())
                        .stations(createStationList(line.getDownStationId(), line.getUpStationId()))
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public UpdateLineResponse updateLine(Long id, CreateLineRequest req) {
        // 존재하지 않는 노선을 조회했을 때 예외처리
        Line line = lineRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        line.setColor(req.getName());
        line.setDistance(req.getDistance());
        line.setName(req.getName());
        line.setDownStationId(req.getDownStationId());
        line.setUpStationId(req.getUpStationId());

        return UpdateLineResponse.builder()
                .color(req.getColor())
                .distance(req.getDistance())
                .name(req.getName())
                .downStationId(req.getDownStationId())
                .upStationId(req.getUpStationId()).build();
    }

    // 노선의 역 리스트 생성 함수
    private List<Station> createStationList(Long downStationId, Long upStationId) {
        Station downStation = stationRepository.findById(downStationId).get();
        Station upStation = stationRepository.findById(upStationId).get();

        List<Station> stations = new ArrayList<>();
        stations.add(downStation);
        stations.add(upStation);
        return stations;
    }

}
