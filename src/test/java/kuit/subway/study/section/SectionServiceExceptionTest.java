package kuit.subway.study.section;

import jakarta.transaction.Transactional;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateBothExistException;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateBothNotExistExcpetion;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateLengthLongerException;
import kuit.subway.exception.badrequest.section.delete.InvalidSectionDeleteOnlyTwoStationsException;
import kuit.subway.exception.badrequest.section.delete.InvalidSectionDeleteStationNotExist;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.service.LineService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Transactional
@DisplayName("지하철 구간 Classic 예외 테스트")
public class SectionServiceExceptionTest {

    @Autowired
    private LineService lineService;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Nested
    @DisplayName("지하철 구간 생성 예외 테스트")
    class AddSectionException {
        Station station1;
        Station station2;
        Station station3;
        Station station4;
        Line line;

        // 사전에 필요한 역, 노선 데이터 생성
        @BeforeEach
        void setUpLine() {

            station1 = Station.createStation("강남역");
            station2 = Station.createStation("수서역");
            station3 = Station.createStation("논현역");
            station4 = Station.createStation("이수역");

            stationRepository.save(station1);
            stationRepository.save(station2);
            stationRepository.save(station3);
            stationRepository.save(station4);

            line = Line.createLine("와우선", "green", 20);
            line.addSection(Section.createSection(line, station1, station2, 10));
            lineRepository.save(line);
        }

        @Test
        @DisplayName("상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가 불가")
        void StationsBothNotExist() {

            // given
            SectionCreateRequest req = new SectionCreateRequest(station3.getId(), station4.getId(), 2);

            // when
            // then
            Assertions.assertThatThrownBy(() -> lineService.addSection(line.getId(), req))
                    .isInstanceOf(InvalidSectionCreateBothNotExistExcpetion.class);
        }

        @Test
        @DisplayName("상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가 불가")
        void StationsBothExist() {

            // given
            SectionCreateRequest req = new SectionCreateRequest(station2.getId(), station1.getId(), 2);

            // when
            // then
            Assertions.assertThatThrownBy(() -> lineService.addSection(line.getId(), req))
                    .isInstanceOf(InvalidSectionCreateBothExistException.class);
        }

        @Test
        @DisplayName("역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크거나 같으면 추가 불가")
        void StationsBetweenLengthLonger() {

            // given
            SectionCreateRequest req = new SectionCreateRequest(station1.getId(), station3.getId(), 200);

            // when
            // then
            Assertions.assertThatThrownBy(() -> lineService.addSection(line.getId(), req))
                    .isInstanceOf(InvalidSectionCreateLengthLongerException.class);
        }

    }

    @Nested
    @DisplayName("지하철 구간 삭제 예외 테스트")
    class DeleteSectionException {
        Station station1;
        Station station2;
        Station station3;

        Station station4;
        Line line;
        // 사전에 필요한 역, 노선 데이터 생성
        @BeforeEach
        void setUpLine() {

            station1 = Station.createStation("강남역");
            station2 = Station.createStation("수서역");
            station3 = Station.createStation("논현역");
            station4 = Station.createStation("이수역");

            stationRepository.save(station1);
            stationRepository.save(station2);
            stationRepository.save(station3);
            stationRepository.save(station4);

            line = Line.createLine("와우선", "green", 20);
            line.addSection(Section.createSection(line, station1, station2, 10));
            line.addSection(Section.createSection(line, station2, station3, 10));
            lineRepository.save(line);
        }

        @Test
        @DisplayName("노선에 등록되어 있지 않은 역은 제거 불가")
        void deleteSectionStationNotExist() {

            // given
            SectionDeleteRequest req = new SectionDeleteRequest(station4.getId());

            // when
            // then
            Assertions.assertThatThrownBy(() -> lineService.deleteSection(line.getId(), req))
                    .isInstanceOf(InvalidSectionDeleteStationNotExist.class);

        }

        @Test
        @DisplayName("구간이 하나인 노선에서 구간 제거 불가")
        void deleteSectionOnlyOneExist() {

            // given
            SectionDeleteRequest req = new SectionDeleteRequest(station3.getId());
            lineService.deleteSection(line.getId(), req);

            // when
            SectionDeleteRequest req2 = new SectionDeleteRequest(station2.getId());

            // then
            Assertions.assertThatThrownBy(() -> lineService.deleteSection(line.getId(), req2))
                    .isInstanceOf(InvalidSectionDeleteOnlyTwoStationsException.class);
        }

    }
}
