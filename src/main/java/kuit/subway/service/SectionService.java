package kuit.subway.service;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.dto.response.line.LineReadResponse;
import kuit.subway.dto.response.section.SectionCreateResponse;
import kuit.subway.dto.response.section.SectionDeleteResponse;
import kuit.subway.dto.response.station.StationReadResponse;
import kuit.subway.exception.badrequest.station.InvalidLineStationException;
import kuit.subway.exception.notfound.line.NotFoundLineException;
import kuit.subway.exception.notfound.station.NotFoundStationException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SectionService {
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

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
