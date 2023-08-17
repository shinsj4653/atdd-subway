package kuit.subway.service;

import kuit.subway.domain.Line;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.response.line.CreateLineResponse;
import kuit.subway.dto.response.station.CreateStationResponse;
import kuit.subway.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LineService {

    private final LineRepository lineRepository;

    @Transactional
    public CreateLineResponse addLine(CreateLineRequest res) {
        Line line = new Line(res.getColor(), res.getDistance(), res.getName(), res.getDownStationId(), res.getUpStationId());
        lineRepository.save(line);
        return new CreateLineResponse(line.getId());
    }
}
