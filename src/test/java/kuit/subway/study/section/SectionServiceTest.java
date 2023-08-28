package kuit.subway.study.section;

import jakarta.transaction.Transactional;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.service.LineService;
import kuit.subway.utils.fixtures.SectionFixtures;
import kuit.subway.utils.steps.SectionStep;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@DisplayName("지하철 구간 Classic 테스트")
public class SectionServiceTest {

    @Autowired
    private LineService lineService;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Nested
    @DisplayName("지하철 구간 생성 테스트")
    class AddSection {
        Station station1;
        Station station2;
        Station station3;
        Line line;

        // 사전에 필요한 역, 노선 데이터 생성
        @BeforeEach
        void setUpLine() {

            station1 = Station.createStation("강남역");
            station2 = Station.createStation("수서역");
            station3 = Station.createStation("논현역");

            stationRepository.save(station1);
            stationRepository.save(station2);
            stationRepository.save(station3);

            line = Line.createLine("와우선", "green", 20);
            line.addSection(Section.createSection(line, station1, station2, 10));
            lineRepository.save(line);
        }

        @Test
        @DisplayName("지하철 구간 생성 - 새로운 역을 상행 종점으로 등록할 경우 맨 상위 구간으로 추가")
        void addSectionFirstUpStation() {

            // given
            SectionCreateRequest req = SectionStep.지하철_구간_생성_요청(station3.getId(), station1.getId(), 5);
            lineService.addSection(line.getId(), req);

            // when
            List<Section> orderSections = line.getSections().getOrderSections();

            // then
            assertEquals(station3.getId(), orderSections.get(0).getUpStation().getId());

        }

        @Test
        @DisplayName("지하철 구간 생성 - 새로운 역을 하행 종점으로 등록할 경우 맨 하위 구간으로 추가")
        void addSectionLastDownStation() {

            // given
            SectionCreateRequest req = SectionStep.지하철_구간_생성_요청(station2.getId(), station3.getId(), 5);
            lineService.addSection(line.getId(), req);

            // when
            List<Section> orderSections = line.getSections().getOrderSections();

            // then
            assertEquals(station3.getId(), orderSections.get(orderSections.size() - 1).getDownStation().getId());

        }

        @Test
        @DisplayName("사이에 끼울 경우 각 기존 구간의 상행역 or 하행역을 신규 구간 정보로 잘 변경 - 새로운 구간의 상행역이 이미 존재할 경우")
        void addSectionBetweenStationsUpExist() {

            // given
            SectionCreateRequest req = SectionStep.지하철_구간_생성_요청(station1.getId(), station3.getId(), 5);
            lineService.addSection(line.getId(), req);

            // when
            List<Section> orderSections = line.getSections().getOrderSections();

            // then
            assertEquals(station3.getId(), orderSections.get(0).getDownStation().getId());
            assertEquals(station2.getId(), orderSections.get(1).getDownStation().getId());
            assertEquals(2, orderSections.size());
        }

        @Test
        @DisplayName("사이에 끼울 경우 각 기존 구간의 상행역 or 하행역을 신규 구간 정보로 잘 변경 - 새로운 구간의 하행역이 이미 존재할 경우")
        void addSectionBetweenStationsDownExist() {

            // given
            SectionCreateRequest req = SectionStep.지하철_구간_생성_요청(station3.getId(), station2.getId(), 5);
            lineService.addSection(line.getId(), req);

            // when
            List<Section> orderSections = line.getSections().getOrderSections();

            // then
            assertEquals(station3.getId(), orderSections.get(0).getDownStation().getId());
            assertEquals(station2.getId(), orderSections.get(1).getDownStation().getId());
            assertEquals(2, orderSections.size());
        }

        @Test
        @DisplayName("노선 조회 시 상행 종점을 기준으로 역들이 잘 정렬되어 반환되어야 함")
        void addSectionReturnAsSorted() {

            // given
            SectionCreateRequest req = SectionStep.지하철_구간_생성_요청(station3.getId(), station2.getId(), 5);
            lineService.addSection(line.getId(), req);

            // when
            List<Section> orderSections = line.getSections().getOrderSections();

            // then
            assertEquals(station1.getId(), orderSections.get(0).getUpStation().getId());
            assertEquals(station3.getId(), orderSections.get(0).getDownStation().getId());
            assertEquals(station2.getId(), orderSections.get(1).getDownStation().getId());
        }
    }

    @Nested
    @DisplayName("지하철 구간 삭제 테스트")
    class DeleteSection {
        Station station1;
        Station station2;
        Station station3;
        Line line;

        // 사전에 필요한 역, 노선 데이터 생성
        @BeforeEach
        void setUpLine() {

            station1 = Station.createStation("강남역");
            station2 = Station.createStation("수서역");
            station3 = Station.createStation("논현역");

            stationRepository.save(station1);
            stationRepository.save(station2);
            stationRepository.save(station3);

            line = Line.createLine("와우선", "green", 20);
            line.addSection(Section.createSection(line, station1, station2, 10));
            line.addSection(Section.createSection(line, station2, station3, 10));
            lineRepository.save(line);
        }

        @Test
        @DisplayName("하행 종점역이 제거될 경우 다음으로 오던 역이 하행 종점이 되도록 구현")
        void deleteLastStation() {

            // given
            SectionDeleteRequest req = SectionStep.지하철_구간_삭제_요청(station3.getId());
            lineService.deleteSection(line.getId(), req);

            // when
            List<Section> orderSections = line.getSections().getOrderSections();

            // then
            assertEquals(station2.getId(), orderSections.get(orderSections.size() - 1).getDownStation().getId());

        }

        @Test
        @DisplayName("상행 종점역이 제거될 경우 다음으로 오던 역이 상행 종점이 되도록 구현")
        void deleteFirstStation() {

            // given
            SectionDeleteRequest req = SectionStep.지하철_구간_삭제_요청(station1.getId());
            lineService.deleteSection(line.getId(), req);

            // when
            List<Section> orderSections = line.getSections().getOrderSections();

            // then
            assertEquals(station2.getId(), orderSections.get(0).getUpStation().getId());

        }

        @Test
        @DisplayName("중간역이 제거될 경우 재배치해야한다.")
        void deleteBetweenStation() {

            // given
            SectionDeleteRequest req = SectionStep.지하철_구간_삭제_요청(station2.getId());
            lineService.deleteSection(line.getId(), req);

            // when
            List<Section> orderSections = line.getSections().getOrderSections();

            // then
            assertEquals(station1.getId(), orderSections.get(0).getUpStation().getId());
            assertEquals(station3.getId(), orderSections.get(0).getDownStation().getId());
            assertEquals(20, orderSections.get(0).getDistance());

        }

    }

}
