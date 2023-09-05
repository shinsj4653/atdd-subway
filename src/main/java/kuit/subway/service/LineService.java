package kuit.subway.service;

import kuit.subway.domain.Graph;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.dto.response.common.LineReadResponse;
import kuit.subway.dto.response.line.*;
import kuit.subway.exception.badrequest.line.InvalidPathSameStationException;
import kuit.subway.exception.badrequest.station.InvalidLineStationException;
import kuit.subway.exception.notfound.line.NotFoundLineException;
import kuit.subway.exception.notfound.station.NotFoundStationException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.SectionRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final SectionRepository sectionRepository;

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
                .map(LineReadResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public LineReadResponse updateLine(Long id, LineUpdateRequest req) {
        // 존재하지 않는 노선을 수정하려 했을때 예외처리
        Line line = validateLineExist(id);
        // 상행역과 하행역이 같으면 예외처리

        // 모든 예외조건 패스할 시, request 대로 노선 수정
        line.updateLine(req.getName(), req.getColor(), req.getLineDistance());

        return LineReadResponse.of(line);
    }

    @Transactional
    public LineDeleteResponse deleteLine(Long id) {

        // 존재하지 않는 노선을 삭제하려고 할시, 예외처리
        Line line = validateLineExist(id);
        return LineDeleteResponse.of(line);
    }

    @Transactional
    public LineReadResponse addSection(Long lineId, SectionCreateRequest req) {

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

        return LineReadResponse.of(line);
    }

    @Transactional
    public LineReadResponse deleteSection(Long lineId, SectionDeleteRequest req) {

        Long deleteStationId = req.getDeleteStationId();

        // 역에 대한 예외처리
        Station station = validateStationExist(deleteStationId);

        // 노선에 대한 예외처리
        Line line = validateLineExist(lineId);

        // 노선의 구간 삭제
        line.deleteSection(station);
        return LineReadResponse.of(line);
    }

    public PathReadResponse findPath(Long startStationId, Long endStationId) {

        // 존재하지 않는 역을 경로 조회 요청으로 사용시 예외발생
        Station startStation = validateStationExist(startStationId);
        Station endStation = validateStationExist(endStationId);

        // 출발역과 도착역이 같을 때 예외발생
        validateFindPathSameStations(startStationId, endStationId);

        // 경로 조회 -> 모든 노선들이 하나의 그래프 형태로 되어 있어야 한다
        List<Line> lines = lineRepository.findAll();
        //List<Section> sections1 = sectionRepository.findByUpStationIdOrDownStationId(req.getStartStationId(), req.getEndStationId());
        //List<Section> sections2 =  sectionRepository.findByUpStationIdOrDownStationId(req.getEndStationId(), req.getStartStationId());

//        Set<Section> set = new LinkedHashSet<>(sections1);
//        set.addAll(sections2);
//        List<Section> mergedSectionList = new ArrayList<>(set);

        Graph graph = new Graph(lines);
        graph.initPath();

        return graph.getShortestPath(startStation, endStation);
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
