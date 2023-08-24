package kuit.subway.study.line;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.response.line.LineCreateResponse;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.exception.notfound.line.NotFoundLineException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.service.LineService;
import kuit.subway.utils.fixtures.LineFixtures;
import kuit.subway.utils.fixtures.StationFixtures;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.when;
import static kuit.subway.utils.fixtures.StationFixtures.지하철_역_등록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        given(stationRepository.findById(2L)).willReturn(Optional.of(upStation));

        Line line = Line.createLine("와우선", "green", 20);
        line.addSection(Section.createSection(line, upStation, downStation, 1));

        LineCreateRequest req = new LineCreateRequest("와우선", "green", 20, 1L, 2L);

        given(lineRepository.save(line)).willReturn(line);
        given(lineRepository.findById(1L)).willReturn(Optional.of(line));

        // when
        LineCreateResponse res = lineService.addLine(req);
        Optional<Line> findLine = lineRepository.findById(1L);

        // then
        assertThat(findLine).isNotNull();
        assertEquals(findLine.get().getId(), res.getId());
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
        assertEquals(findLine.getId(), line.getId());
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


//    @DisplayName("노선 생성 Mock 테스트")
//    @Test
//    void addSection() {
//        // given
//        // lineRepository, stationRepository stub 설정을 통해 초기값 셋팅
//        // https://velog.io/@lxxjn0/Mockito%EC%99%80-BDDMockito%EB%8A%94-%EB%AD%90%EA%B0%80-%EB%8B%A4%EB%A5%BC%EA%B9%8C
//        // BDDMockito를 사용한 given으로 변경 가능
//        // 이름만 다르고 기능은 모두 같음
//        when(stationRepository.findById(1L)).thenReturn(StationFixData.create_강남역());
//        when(stationRepository.findById(2L)).thenReturn(StationFixData.create_성수역());
//        when(lineRepository.save(경춘선)).thenReturn(createEmptyLine_경춘선());

//
//        // when
//        // lineService.addSection 호출
//        LineResponse lineResponse = lineService.saveLine(경춘선_요청);
//
//        // then
//        // lineService.findLineById 메서드를 통해 검증
//        assertEquals("경춘", lineResponse.getName());
//        // verify를 통해 해당 메서드가 실제 몇번 불렸는지도 알 수 있다.
//        verify(stationRepository, times(2)).findById();
//    }
//


}
