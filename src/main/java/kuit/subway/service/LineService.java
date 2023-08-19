package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Line;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.response.line.CreateLineResponse;
import kuit.subway.dto.response.line.DeleteLineResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.line.UpdateLineResponse;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.dto.response.station.DeleteStationResponse;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.exception.badrequest.InvalidLineStationException;
import kuit.subway.exception.notfound.NotFoundLineException;
import kuit.subway.exception.notfound.NotFoundStationException;
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

        Long downStationId = res.getDownStationId();
        Long upStationId = res.getUpStationId();

        // 존재하지 않는 station_id를 추가하려고 하면 예외발생
        validateStationExist(downStationId);
        validateStationExist(upStationId);

        // 상행역과 하행역 둘 다 같은 역이면 예외발생
        validateSameStation(downStationId, upStationId);

        // 만약, 둘 다 존재하는 역이라면 노선 생성
        Line line = Line.builder()
                        .color(res.getColor())
                        .distance(res.getDistance())
                        .name(res.getName())
                        .downStationId(res.getDownStationId())
                        .upStationId(res.getUpStationId())
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now()).build();

        lineRepository.save(line);

        return new CreateLineResponse(line.getId());
    }

    @Transactional(readOnly = true)
    public LineDto findLineById(Long id) {

        // 존재하지 않는 노선을 조회했을 때 예외처리
        Line line = validateLineExist(id);

        LineDto result = LineDto.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .stations(createStationList(line.getDownStationId(), line.getUpStationId()))
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
                        .stations(createStationList(line.getDownStationId(), line.getUpStationId()))
                        .createdDate(line.getCreatedDate())
                        .modifiedDate(line.getModifiedDate())
                        .build())
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public UpdateLineResponse updateLine(Long id, CreateLineRequest req) {
        // 존재하지 않는 노선을 수정하려 했을때 예외처리
        Line line = validateLineExist(id);

        // 존재하지 않는 station_id로 변경하려 했을 때 예외처리
        validateStationExist(req.getDownStationId());
        validateStationExist(req.getUpStationId());

        // 상행역과 하행역이 같으면 예외처리
        validateSameStation(req.getDownStationId(), req.getUpStationId());

        // 존재한다면, request 대로 노선 수정
        line.setColor(req.getColor());
        line.setDistance(req.getDistance());
        line.setName(req.getName());
        line.setDownStationId(req.getDownStationId());
        line.setUpStationId(req.getUpStationId());
        line.setModifiedDate(LocalDateTime.now());

        return UpdateLineResponse.builder()
                .color(req.getColor())
                .distance(req.getDistance())
                .name(req.getName())
                .downStationId(req.getDownStationId())
                .upStationId(req.getUpStationId()).build();
    }

    @Transactional
    public DeleteLineResponse deleteLine(Long id) {

        // 존재하지 않는 노선을 삭제하려고 할시, 예외처리
        Line line = validateLineExist(id);

        lineRepository.delete(line);
        return new DeleteLineResponse(line.getId());
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
