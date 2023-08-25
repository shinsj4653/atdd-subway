package kuit.subway.service;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Sections;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.response.line.LineCreateResponse;
import kuit.subway.dto.response.line.LineDeleteResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.line.LineUpdateResponse;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.exception.badrequest.station.InvalidLineStationException;
import kuit.subway.exception.notfound.line.NotFoundLineException;
import kuit.subway.exception.notfound.station.NotFoundStationException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
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
        Line line = Line.createLine(res.getName(), res.getColor(), res.getDistance());

        // 노선에는 구간 형태로 추가해줘야한다.
        line.addSection(Section.createSection(line, upStation, downStation, 1));
        lineRepository.save(line);

        return new LineCreateResponse("지하철 노선 생성 완료", line.getId());
    }

    public LineDto findLineById(Long id) {

        // 존재하지 않는 노선을 조회했을 때 예외처리
        Line line = validateLineExist(id);

        return LineDto.createLineDto(line.getId(), line.getName(), line.getColor(), line.getDistance(), getStationDtoList(line.getSections().getOrderSections()));
    }

    public List<LineDto> findAllLines() {

        List<Line> findLines = lineRepository.findAll();
        List<LineDto> result = findLines.stream()
                .map(line -> LineDto.createLineDto(line.getId(), line.getName(), line.getColor(), line.getDistance(), getStationDtoList(line.getSections().getOrderSections())))
                .collect(Collectors.toList());
        return result;
    }

    public List<StationDto> findPath() {

    }

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
        line.updateLine(req.getName(), req.getColor(), req.getDistance(), upStation, downStation);

        return LineUpdateResponse.createLineUpdateResponse(
                line.getId(),
                req.getName(),
                req.getColor(),
                req.getDistance(),
                getStationDtoList(line.getSections().getOrderSections())
        );
    }

    @Transactional
    public LineDeleteResponse deleteLine(Long id) {

        // 존재하지 않는 노선을 삭제하려고 할시, 예외처리
        Line line = validateLineExist(id);

        lineRepository.delete(line);
        return new LineDeleteResponse("지하철 노선 삭제 완료", line.getId());
    }

    private List<StationDto> getStationDtoList(List<Section> sections) {

            List<StationDto> result = new ArrayList<>();
            Long nextUpStationId;

            // 맨 처음 첫 구간은 상행, 하행 둘 다 삽입
            Station upStation = sections.get(0).getUpStation();
            result.add(StationDto.createStationDto(upStation.getId(), upStation.getName()));

            Station downStation = sections.get(0).getDownStation();
            result.add(StationDto.createStationDto(downStation.getId(), downStation.getName()));

            nextUpStationId = downStation.getId();

            for (int i = 0; i < sections.size() - 1; i++) {
                Long finalNextUpStationId = nextUpStationId;
                Section findSection = sections.stream()
                        .filter(section -> section.getUpStation().getId().equals(finalNextUpStationId))
                        .findFirst().get();
                System.out.println(findSection.getDownStation().getId());
                downStation = findSection.getDownStation();
                result.add(StationDto.createStationDto(downStation.getId(), downStation.getName()));
                nextUpStationId = downStation.getId();
            }

            return result;
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
