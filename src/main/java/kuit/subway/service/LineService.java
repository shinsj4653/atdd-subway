package kuit.subway.service;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.line.PathReadRequest;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.dto.response.line.*;
import kuit.subway.dto.response.section.SectionCreateResponse;
import kuit.subway.dto.response.section.SectionDeleteResponse;
import kuit.subway.dto.response.station.StationReadResponse;
import kuit.subway.exception.badrequest.line.InvalidPathSameStationException;
import kuit.subway.exception.badrequest.station.InvalidLineStationException;
import kuit.subway.exception.notfound.line.NotFoundLineException;
import kuit.subway.exception.notfound.station.NotFoundStationException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

        // 상행역과 하행역 둘 다 같은 역이면 예외발생
        validateSameStation(downStationId, upStationId);

        // 만약, 둘 다 존재하는 역이라면 노선 생성
        Line line = Line.createLine(res.getName(), res.getColor(), res.getLineDistance());

        // 노선에는 구간 형태로 추가해줘야한다.
        line.addSection(Section.createSection(line, upStation, downStation, res.getSectionDistance()));
        lineRepository.save(line);

//        return new LineCreateResponse("지하철 노선 생성 완료", line.getId());
        return LineCreateResponse.of(line);
    }

    public LineReadResponse findLineById(Long id) {

        // 존재하지 않는 노선을 조회했을 때 예외처리
        Line line = validateLineExist(id);
        return LineReadResponse.of(line);
    }

    public List<LineReadResponse> findAllLines() {

        List<Line> findLines = lineRepository.findAll();

        return findLines.stream()
                .map(line -> LineReadResponse.of(line))
                .collect(Collectors.toList());
    }

    // 존재하는 역이긴 하지만, 해당 노선에는 존재하지 않으면 오류
//    private void validateFindPathStationsConnected(Line line, Long startStationId, Long endStationId) {
//        Optional<Section> startSection = line.getSections().getOrderSections().stream()
//                .filter(s -> s.getUpStation().getId().equals(startStationId)).findAny();
//
//        Optional<Section> endSection = line.getSections().getOrderSections().stream()
//                .filter(s -> s.getUpStation().getId().equals(endStationId)).findAny();
//
//        if (startSection.isEmpty() || endSection.isEmpty()) {
//            throw new InvalidPathNotConnectedException();
//        }
//    }

    @Transactional
    public LineUpdateResponse updateLine(Long id, LineUpdateRequest req) {
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

        // 모든 예외조건 패스할 시, request 대로 노선 수정
        line.updateLine(req.getName(), req.getColor(), req.getLineDistance(), upStation, downStation, req.getSectionDistance());

        return LineUpdateResponse.of(line);
    }

    @Transactional
    public LineDeleteResponse deleteLine(Long id) {

        // 존재하지 않는 노선을 삭제하려고 할시, 예외처리
        Line line = validateLineExist(id);
        return LineDeleteResponse.of(line);
    }

    @Transactional
    public SectionCreateResponse addSection(Long lineId, SectionCreateRequest req) {

        Long upStationId = req.getUpStationId();
        Long downStationId = req.getDownStationId();

        // 역에 대한 예외처리
        Station upStation = validateStationExist(upStationId);
        Station downStation = validateStationExist(downStationId);
        validateSameStation(upStationId, downStationId);

        // 노선에 대한 예외처리
        Line line = validateLineExist(lineId);

        // 노선에는 구간 형태로 추가해줘야한다.
        line.addSection(Section.createSection(line, upStation, downStation, req.getDistance()));

        return SectionCreateResponse.of(line);
    }

    @Transactional
    public SectionDeleteResponse deleteSection(Long lineId, SectionDeleteRequest req) {

        Long deleteStationId = req.getDeleteStationId();

        // 역에 대한 예외처리
        Station station = validateStationExist(deleteStationId);

        // 노선에 대한 예외처리
        Line line = validateLineExist(lineId);

        // 노선의 구간 삭제
        line.deleteSection(station);
        return SectionDeleteResponse.of(line);
    }

    public PathReadResponse findPath(Long lineId, PathReadRequest req) {

        // 존재하지 않는 역을 경로 조회 요청으로 사용시 예외발생
        Station startStation = validateStationExist(req.getStartStationId());
        Station endStation = validateStationExist(req.getEndStationId());

        // 존재하지 않는 노선을 조회하려 했을때 예외처리
        Line line = validateLineExist(lineId);

        // 출발역과 도착역이 같을 때 예외발생
        validateFindPathSameStations(req.getStartStationId(), req.getEndStationId());

        GraphPath<Station, DefaultWeightedEdge> path = line.getGraphPath(startStation, endStation);

        PathReadResponse res = PathReadResponse.of(getStationDtoPath(path.getVertexList()), path.getWeight());

        return res;
    }

    private List<StationReadResponse> getStationDtoPath(List<Station> path) {
        return path.stream()
                .map(station -> StationReadResponse.of(station))
                .collect(Collectors.toList());
    }

    // 존재하는 역인지 판별해주는 함수
    private Station validateStationExist(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(NotFoundStationException::new);
    }

    // 노선으로 등록할 두 역이 같은 역인지 판별해주는 함수
    private void validateSameStation(Long downStationId, Long upStationId) {
        if (downStationId.equals(upStationId)) {
            throw new InvalidLineStationException();
        }
    }

    // 존재하는 노선인지 판별해주는 함수
    private Line validateLineExist(Long id) {
        return lineRepository.findById(id)
                .orElseThrow(NotFoundLineException::new);


    }

    // 경로 조회 - 출발역과 도착역이 같은 경우를 판별해주는 함수
    private void validateFindPathSameStations(Long startStationId, Long endStationId) {
        if (startStationId.equals(endStationId)) {
            throw new InvalidPathSameStationException();
        }
    }
}
