package kuit.subway.service;

import jakarta.persistence.EntityNotFoundException;
import kuit.subway.domain.Line;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.response.line.CreateLineResponse;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
