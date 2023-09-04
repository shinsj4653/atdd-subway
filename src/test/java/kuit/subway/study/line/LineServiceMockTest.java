package kuit.subway.study.line;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.line.PathReadRequest;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.response.line.*;
import kuit.subway.dto.response.station.StationReadResponse;
import kuit.subway.exception.badrequest.line.InvalidPathNotConnectedException;
import kuit.subway.exception.badrequest.line.InvalidPathSameStationException;
import kuit.subway.exception.badrequest.station.InvalidLineStationException;
import kuit.subway.exception.notfound.line.NotFoundLineException;
import kuit.subway.exception.notfound.station.NotFoundStationException;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.service.LineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Nested
    @DisplayName("지하철 노선 생성 Mock 테스트")
    class AddLine {

        Station upStation;
        Station downStation;
        Station notExistStation;

        @BeforeEach
        void setUp() {
            upStation = Station.createStation("강남역");
            downStation = Station.createStation("수서역");

        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("새로운 노선 생성")
            void addLineSuccess() {
                // given
                given(stationRepository.findById(1L)).willReturn(Optional.of(upStation));
                given(stationRepository.findById(2L)).willReturn(Optional.of(downStation));

                Line line = Line.createLine("와우선", "green", 20);
                line.addSection(Section.createSection(line, upStation, downStation, 5));

                LineCreateRequest req = new LineCreateRequest("와우선", "green", 20, 1L, 2L, 5);

                given(lineRepository.save(line)).willReturn(line);
                given(lineRepository.findById(line.getId())).willReturn(Optional.of(line));

                // when
                LineCreateResponse res = lineService.addLine(req);
                Optional<Line> findLine = lineRepository.findById(line.getId());

                // then
                assertThat(findLine).isNotNull();
                assertEquals(line.getId(), res.getId());
                verify(lineRepository).save(line);
                verify(stationRepository, times(2)).findById(any());
                verify(lineRepository, times(1)).findById(any());
            }

        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("존재하지 않는 역을 노선에 추가할 경우")
            void addLineFail1() {
                // given
                given(stationRepository.findById(3L)).willReturn(Optional.ofNullable(null));

                Line line = Line.createLine("와우선", "green", 20);

                // when
                LineCreateRequest req = new LineCreateRequest("와우선", "green", 20, 1L, 3L, 5);

                // then
                assertThatThrownBy(() -> lineService.addLine(req)).isInstanceOf(NotFoundStationException.class);

            }

            @Test
            @DisplayName("같은 상행역과 하행역을 노선에 추가할 경우")
            void addLineFail2() {
                // given
                given(stationRepository.findById(1L)).willReturn(Optional.ofNullable(upStation));

                Line line = Line.createLine("와우선", "green", 20);


                // when
                LineCreateRequest req = new LineCreateRequest("와우선", "green", 20, 1L, 1L, 5);

                // then
                assertThatThrownBy(() -> lineService.addLine(req)).isInstanceOf(InvalidLineStationException.class);

            }
        }


    }

    @Nested
    @DisplayName("지하철 노선 식별자로 조회 Mock 테스트")
    class FindLineById {

        Station upStation;
        Station downStation;

        @BeforeEach
        void setUp() {
            upStation = Station.createStationWithId(1L,"강남역");
            downStation = Station.createStationWithId(2L, "수서역");
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("식별자로 노선 조회")
            void findLineByIdSuccess() {
                // given
                Line line = Line.createLine("와우선", "green", 20);
                line.addSection(Section.createSection(line, upStation, downStation, 5));

                given(lineRepository.findById(1L)).willReturn(Optional.of(line));

                // when
                LineReadResponse findLine = lineService.findLineById(1L);

                // then
                assertThat(findLine).isNotNull();
                assertEquals(line.getId(), findLine.getId());
                verify(lineRepository, times(1)).findById(any());
            }

        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("존재하지 않는 노선을 조회할 경우")
            void findLineByIdFail() {
                // given
                given(lineRepository.findById(2L)).willReturn(Optional.ofNullable(null));

                // when
                // then
                assertThatThrownBy(() -> lineService.findLineById(2L)).isInstanceOf(NotFoundLineException.class);

            }
        }
    }

    @Nested
    @DisplayName("지하철 노선 전체조회 Mock 테스트")
    class FindAllLines {
        Station upStation;
        Station downStation;

        @BeforeEach
        void setUp() {
            upStation = Station.createStationWithId(1L, "강남역");
            downStation = Station.createStationWithId(2L, "수서역");
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("노선 전체 조회")
            void findAllLinesSuccess() {
                // given
                Line line1 = Line.createLine("와우선", "green", 20);
                Line line2 = Line.createLine("경춘선", "blue", 20);
                line1.addSection(Section.createSection(line1, upStation, downStation, 1));
                line2.addSection(Section.createSection(line1, upStation, downStation, 1));

                List<Line> lines = new ArrayList<>();
                lines.add(line1);
                lines.add(line2);

                given(lineRepository.findAll()).willReturn(lines);

                // when
                List<LineReadResponse> allLines = lineService.findAllLines();

                // then
                assertThat(allLines).isNotNull();
                assertEquals(2, allLines.size());
                verify(lineRepository, times(1)).findAll();
            }
        }
    }

    @Nested
    @DisplayName("지하철 노선 수정 Mock 테스트")
    class UpdateLine {

        Station upStation;
        Station downStation;

        @BeforeEach
        void setUp() {
            upStation = Station.createStationWithId(1L,"강남역");
            downStation = Station.createStationWithId(2L, "수서역");
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("노선 정보 수정")
            void updateLineSuccess() {

                Line line = Line.createLine("와우선", "green", 20);
                line.addSection(Section.createSection(line, upStation, downStation, 1));

                given(lineRepository.findById(line.getId())).willReturn(Optional.of(line));

                // when
                LineUpdateRequest req = new LineUpdateRequest("경춘선", "blue", 15);
                LineUpdateResponse res = lineService.updateLine(line.getId(), req);

                // then
                assertThat(res).isNotNull();
                assertEquals(line.getId(), res.getId());
                assertEquals(line.getName(), res.getName());
                verify(lineRepository, times(1)).findById(any());
            }

        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("존재하지 않는 노선을 수정하려고 할 경우")
            void updatedLineFail() {
                // given
                given(lineRepository.findById(2L)).willReturn(Optional.ofNullable(null));

                // when
                // then
                assertThatThrownBy(() -> lineService.findLineById(2L)).isInstanceOf(NotFoundLineException.class);

            }
        }
    }
    @Nested
    @DisplayName("지하철 노선 삭제 Mock 테스트")
    class DeleteLine {

        Station upStation;
        Station downStation;

        @BeforeEach
        void setUp() {
            upStation = Station.createStation("강남역");
            downStation = Station.createStation("수서역");
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("노선 정보 삭제")
            void deleteLineSuccess() {
                // given
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

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("존재하지 않는 노선을 삭제하려고 할 경우")
            void updatedLineFail() {
                // given
                given(lineRepository.findById(2L)).willReturn(Optional.ofNullable(null));

                // when
                // then
                assertThatThrownBy(() -> lineService.findLineById(2L)).isInstanceOf(NotFoundLineException.class);

            }
        }


    }

    @Nested
    @DisplayName("노선 내 경로 조회 Mock 테스트")
    class FindPath {
        Station station1;
        Station station2;
        Station station3;

        @BeforeEach
        void setUp() {
            station1 = Station.createStationWithId(1L, "강남역");
            station2 = Station.createStationWithId(2L, "수서역");
            station3 = Station.createStationWithId(3L, "논현역");
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {

            @Test
            @DisplayName("출발역 id와 도착역 id로 요청하면 출발역, 도착역까지의 경로에 있는 역 목록, 그리고 경로 구간의 총 거리가 검색된다.")
            void findLineSuccess() {

                // given
                given(stationRepository.findById(1L)).willReturn(Optional.of(station1));
                given(stationRepository.findById(2L)).willReturn(Optional.of(station2));
                given(stationRepository.findById(3L)).willReturn(Optional.of(station3));

                Line line = Line.createLineWithId(1L, "와우선", "green", 20);
                given(lineRepository.findById(1L)).willReturn(Optional.of(line));

                List<Line> lines = new ArrayList<>();
                lines.add(line);
                given(lineRepository.findAll()).willReturn(lines);

                lineService.addSection(1L, new SectionCreateRequest(1L, 2L, 10));
                lineService.addSection(1L, new SectionCreateRequest(2L, 3L, 10));

                // when

                PathReadRequest req = new PathReadRequest(1L, 3L);
                PathReadResponse res = lineService.findPath(req);

                StationReadResponse stationRes1 = res.getStations().get(0);
                StationReadResponse stationRes2 = res.getStations().get(1);
                StationReadResponse stationRes3 = res.getStations().get(2);

                // then
                assertThat(res).isNotNull();
                assertThat(res.getStations()).containsExactly(stationRes1, stationRes2, stationRes3);
                assertEquals(20.0, res.getTotalDistance());
                verify(lineRepository, times(2)).findById(anyLong());
                verify(lineRepository, times(1)).findAll();
                verify(stationRepository, times(6)).findById(anyLong());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailedCase {

            @Test
            @DisplayName("출발역과 도착역이 같은 경우")
            void findLineFail1() {
                given(stationRepository.findById(1L)).willReturn(Optional.of(station1));
                given(stationRepository.findById(2L)).willReturn(Optional.of(station2));
                given(stationRepository.findById(3L)).willReturn(Optional.of(station3));

                Line line = Line.createLineWithId(1L, "와우선", "green", 20);
                given(lineRepository.findById(1L)).willReturn(Optional.of(line));

                // when
                lineService.addSection(1L, new SectionCreateRequest(1L, 2L, 10));
                lineService.addSection(1L, new SectionCreateRequest(2L, 3L, 10));
                PathReadRequest req = new PathReadRequest(1L, 1L);

                // then
                assertThatThrownBy(() -> lineService.findPath(req)).isInstanceOf(InvalidPathSameStationException.class);
                verify(lineRepository, times(2)).findById(anyLong());
                verify(stationRepository, times(6)).findById(anyLong());
            }

            @Test
            @DisplayName("출발역과 도착역이 연결이 되어 있지 않은 경우")
            void findLineFail2() {
                given(stationRepository.findById(1L)).willReturn(Optional.of(station1));
                given(stationRepository.findById(2L)).willReturn(Optional.of(station2));
                given(stationRepository.findById(3L)).willReturn(Optional.of(station3));

                Line line = Line.createLineWithId(1L, "와우선", "green", 20);
                given(lineRepository.findById(1L)).willReturn(Optional.of(line));

                // when
                lineService.addSection(1L, new SectionCreateRequest(1L, 2L, 10));
                PathReadRequest req = new PathReadRequest(1L, 3L);

                // then
                assertThatThrownBy(() -> lineService.findPath(req)).isInstanceOf(InvalidPathNotConnectedException.class);
                verify(lineRepository, times(1)).findById(anyLong());
                verify(stationRepository, times(4)).findById(anyLong());
            }

            @Test
            @DisplayName("존재하지 않은 출발역이나 도착역을 조회할 경우")
            void findLineFail3() {
                given(stationRepository.findById(1L)).willReturn(Optional.of(station1));
                given(stationRepository.findById(2L)).willReturn(Optional.of(station2));

                Line line = Line.createLineWithId(1L, "와우선", "green", 20);
                given(lineRepository.findById(1L)).willReturn(Optional.of(line));

                // when
                lineService.addSection(1L, new SectionCreateRequest(1L, 2L, 10));
                PathReadRequest req = new PathReadRequest(1L, 3L);

                // then
                assertThatThrownBy(() -> lineService.findPath(req)).isInstanceOf(NotFoundStationException.class);
                verify(lineRepository, times(1)).findById(anyLong());
                verify(stationRepository, times(4)).findById(anyLong());
            }

        }

    }

}
