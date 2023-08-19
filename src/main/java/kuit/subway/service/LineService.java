package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Line;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.response.line.LineCreateResponse;
import kuit.subway.dto.response.line.LineDeleteResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.line.LineUpdateResponse;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.exception.badrequest.InvalidLineStationException;
import kuit.subway.exception.notfound.NotFoundLineException;
import kuit.subway.exception.notfound.NotFoundStationException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    @Transactional
    public LineCreateResponse addLine(LineCreateRequest res) {

        Long downStationId = res.getDownStationId();
        Long upStationId = res.getUpStationId();

        // 존재하지 않는 station_id를 추가하려고 하면 예외발생
        Station downStation = validateStationExist(downStationId);
        Station upStation = validateStationExist(upStationId);

        List<Station> stations = new ArrayList<>();
        stations.add(upStation);
        stations.add(downStation);

        // 상행역과 하행역 둘 다 같은 역이면 예외발생
        validateSameStation(downStationId, upStationId);

        // 만약, 둘 다 존재하는 역이라면 노선 생성
        Line line = Line.builder()
                        .color(res.getColor())
                        .distance(res.getDistance())
                        .name(res.getName())
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now()).build();

        line.addStations(stations);
        lineRepository.save(line);

        return new LineCreateResponse("지하철 노선 생성 완료", line.getId());
    }

    @Transactional(readOnly = true)
    public LineDto findLineById(Long id) {

        // 존재하지 않는 노선을 조회했을 때 예외처리
        Line line = validateLineExist(id);

        LineDto result = LineDto.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .stations(createStationDtoList(line.getStations()))
                .createdDate(line.getCreatedDate())
                .modifiedDate(line.getModifiedDate())
                .build();

        return result;
    }

    @Transactional(readOnly = true)
    public List<LineDto> findAllLines() {

        List<Line> findLines = lineRepository.findAll();
        List<LineDto> result = findLines.stream()
                .map(line -> LineDto.builder()
                        .id(line.getId())
                        .name(line.getName())
                        .color(line.getColor())
                        .stations(createStationDtoList(line.getStations()))
                        .createdDate(line.getCreatedDate())
                        .modifiedDate(line.getModifiedDate())
                        .build())
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public LineUpdateResponse updateLine(Long id, LineCreateRequest req) {
        // 존재하지 않는 노선을 수정하려 했을때 예외처리
        Line line = validateLineExist(id);

        // 존재하지 않는 station_id로 변경하려 했을 때 예외처리
        Station downStation = validateStationExist(req.getDownStationId());
        Station upStation = validateStationExist(req.getUpStationId());

        List<Station> stations = new ArrayList<>();
        stations.add(upStation);
        stations.add(downStation);

        // 상행역과 하행역이 같으면 예외처리
        validateSameStation(req.getDownStationId(), req.getUpStationId());

        // 존재한다면, request 대로 노선 수정
        line.updateLine(req.getColor(), req.getDistance(), req.getName(), LocalDateTime.now());
        line.updateStations(stations);

        return LineUpdateResponse.builder()
                .color(req.getColor())
                .distance(req.getDistance())
                .name(req.getName())
                .downStationId(req.getDownStationId())
                .upStationId(req.getUpStationId()).build();
    }

    @Transactional
    public LineDeleteResponse deleteLine(Long id) {

        // 존재하지 않는 노선을 삭제하려고 할시, 예외처리
        Line line = validateLineExist(id);

        lineRepository.delete(line);
        return new LineDeleteResponse("지하철 노선 삭제 완료", line.getId());
    }

    // 노선의 역 리스트 생성 함수
    private List<StationDto> createStationDtoList(List<Station> stations) {

        List<StationDto> result = stations.stream()
                .map(station -> createStationDto(station)).collect(Collectors.toList());
        return result;
    }
    
    // Station에서 StationDto로 변환해주는 함수
    private StationDto createStationDto(Station station) {
        return StationDto.builder()
                .id(station.getId())
                .name(station.getName())
                .createdDate(station.getCreatedDate())
                .modifiedDate(station.getModifiedDate()).build();
    }

    // 존재하는 역인지 판별해주는 함수
    private Station validateStationExist(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(NotFoundStationException::new);
    }

    // 노선으로 등록할 두 역이 같은 역인지 판별해주는 함수
    private void validateSameStation(Long downStationId, Long upStationId) {
        if (downStationId == upStationId) {
            throw new InvalidLineStationException();
        }
    }

    // 존재하는 노선인지 판별해주는 함수
    private Line validateLineExist(Long id) {
        return lineRepository.findById(id)
                .orElseThrow(NotFoundLineException::new);
    }



}
