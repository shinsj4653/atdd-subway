package kuit.subway.study.line;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.response.line.LineCreateResponse;
import kuit.subway.dto.response.line.LineDeleteResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.line.LineUpdateResponse;
import kuit.subway.exception.notfound.line.NotFoundLineException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.service.LineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("지하철 노선 Mock 테스트")
public class LineServiceMockTest {

    @InjectMocks
    private LineService lineService;

    @Mock
    private LineRepository lineRepository;

    @Mock
    private StationRepository stationRepository;

//    @BeforeEach
//    void setup() {
//        stationRepository.save(Station.createStation("강남역"));
//        stationRepository.save(Station.createStation("성수역"));
//    }

    @DisplayName("지하철 노선 생성 Mock 테스트")
    @Test
    public void createLine() {

        // given
        Station upStation = Station.createStation("강남역");
        Station downStation = Station.createStation("수서역");

        given(stationRepository.findById(1L)).willReturn(Optional.of(upStation));
        given(stationRepository.findById(2L)).willReturn(Optional.of(downStation));

        Line line = Line.createLine("와우선", "green", 20);
        line.addSection(Section.createSection(line, upStation, downStation, 1));

        LineCreateRequest req = new LineCreateRequest("와우선", "green", 20, 1L, 2L);

        given(lineRepository.save(line)).willReturn(line);
        given(lineRepository.findById(line.getId())).willReturn(Optional.of(line));

        // when
        LineCreateResponse res = lineService.addLine(req);
        Optional<Line> findLine = lineRepository.findById(line.getId());

        // then
        assertThat(findLine).isNotNull();
        assertEquals(line.getId(), findLine.get().getId());
        verify(lineRepository).save(line);
        verify(stationRepository, times(2)).findById(any());
        verify(lineRepository, times(1)).findById(any());

    }

    @DisplayName("지하철 노선 식별자로 조회 Mock 테스트")
    @Test
    void findLineById() {
        // given
        Station upStation = Station.createStation("강남역");
        Station downStation = Station.createStation("수서역");

        Line line = Line.createLine("와우선", "green", 20);
        line.addSection(Section.createSection(line, upStation, downStation, 1));

        given(lineRepository.findById(1L)).willReturn(Optional.of(line));

        // when
        LineDto findLine = lineService.findLineById(1L);

        // then
        assertThatThrownBy(() -> lineService.findLineById(2L)).isInstanceOf(NotFoundLineException.class);
        assertThat(findLine).isNotNull();
        assertEquals(line.getId(), findLine.getId());
        verify(lineRepository, times(2)).findById(any());
    }

    @DisplayName("지하철 노선 전체조회 Mock 테스트")
    @Test
    void findAllLines() {
        // given
        Station upStation = Station.createStation("강남역");
        Station downStation = Station.createStation("수서역");

        Line line1 = Line.createLine("와우선", "green", 20);
        Line line2 = Line.createLine("경춘선", "blue", 20);
        line1.addSection(Section.createSection(line1, upStation, downStation, 1));
        line2.addSection(Section.createSection(line1, upStation, downStation, 1));

        List<Line> lines = new ArrayList<>();
        lines.add(line1);
        lines.add(line2);

        given(lineRepository.findAll()).willReturn(lines);

        // when
        List<LineDto> allLines = lineService.findAllLines();

        // then
        assertThat(allLines).isNotNull();
        assertEquals(2, allLines.size());
        verify(lineRepository, times(1)).findAll();
    }

    @DisplayName("지하철 노선 수정 Mock 테스트")
    @Test
    void updateLine() {
        // given
        Station upStation = Station.createStation("강남역");
        Station downStation = Station.createStation("수서역");
        given(stationRepository.findById(1L)).willReturn(Optional.of(upStation));
        given(stationRepository.findById(2L)).willReturn(Optional.of(downStation));

        Line line = Line.createLine("와우선", "green", 20);
        line.addSection(Section.createSection(line, upStation, downStation, 1));

        given(lineRepository.findById(line.getId())).willReturn(Optional.of(line));

        // when
        LineUpdateRequest req = new LineUpdateRequest("경춘선", "blue", 15, 2L, 1L);
        LineUpdateResponse res = lineService.updateLine(line.getId(), req);

        // then
        assertThat(res).isNotNull();
        assertEquals(line.getId(), res.getId());
        assertEquals(line.getName(), res.getName());
        verify(lineRepository, times(1)).findById(any());

    }

    @DisplayName("지하철 노선 삭제 Mock 테스트")
    @Test
    void deleteLine() {
        // given
        Station upStation = Station.createStation("강남역");
        Station downStation = Station.createStation("수서역");
        Line line = Line.createLine("와우선", "green", 20);
        line.addSection(Section.createSection(line, upStation, downStation, 1));

        given(lineRepository.findById(line.getId())).willReturn(Optional.of(line));

        // when
        LineDeleteResponse res = lineService.deleteLine(line.getId());

        // then
        assertThat(res).isNotNull();
        assertEquals(line.getId(), res.getId());
        verify(lineRepository, times(1)).findById(any());

    }



}
