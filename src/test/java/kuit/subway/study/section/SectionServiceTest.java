package kuit.subway.study.section;

import jakarta.transaction.Transactional;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import kuit.subway.service.LineService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@DisplayName("지하철 구간 Classic 테스트")
public class SectionServiceTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Nested
    @DisplayName("지하철 구간 생성 테스트")
    class addSection {
        Station station1;
        Station station2;
        Station station3;
        Line line;

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
            line.addSection(Section.createSection(line, station3, station1, 5));

            // when
            List<StationDto> stationDtoList = getStationDtoList(line.getSections().getOrderSections());

            // then
            assertEquals(station3.getId(), stationDtoList.get(0).getId());

        }

        @Test
        @DisplayName("지하철 구간 생성 - 새로운 역을 하행 종점으로 등록할 경우 맨 하위 구간으로 추가")
        void addSectionLastDownStation() {

            // given
            line.addSection(Section.createSection(line, station2, station3, 5));

            // when
            List<StationDto> stationDtoList = getStationDtoList(line.getSections().getOrderSections());

            // then
            assertEquals(station3.getId(), stationDtoList.get(stationDtoList.size() - 1).getId());

        }

        @Test
        @DisplayName("사이에 끼울 경우 각 기존 구간의 상행역 or 하행역을 신규 구간 정보로 잘 변경 - 새로운 구간의 상행역이 이미 존재할 경우")
        void addSectionBetweenStationsUpExist() {

            // given
            line.addSection(Section.createSection(line, station1, station3, 2));

            // when
            List<StationDto> stationDtoList = getStationDtoList(line.getSections().getOrderSections());

            // then
            assertEquals(station3.getId(), stationDtoList.get(1).getId());
            assertEquals(3, stationDtoList.size());
        }

        @Test
        @DisplayName("사이에 끼울 경우 각 기존 구간의 상행역 or 하행역을 신규 구간 정보로 잘 변경 - 새로운 구간의 하행역이 이미 존재할 경우")
        void addSectionBetweenStationsDownExist() {

            // given
            line.addSection(Section.createSection(line, station3, station2, 2));

            // when
            List<StationDto> stationDtoList = getStationDtoList(line.getSections().getOrderSections());

            // then
            assertEquals(station3.getId(), stationDtoList.get(1).getId());
            assertEquals(3, stationDtoList.size());
        }

        @Test
        @DisplayName("노선 조회 시 상행 종점을 기준으로 역들이 잘 정렬되어 반환되어야 함")
        void addSectionReturnAsSorted() {

            // given
            line.addSection(Section.createSection(line, station3, station2, 2));

            // when
            List<StationDto> stationDtoList = getStationDtoList(line.getSections().getOrderSections());

            // then
            assertEquals(station1.getId(), stationDtoList.get(0).getId());
            assertEquals(station3.getId(), stationDtoList.get(1).getId());
            assertEquals(station2.getId(), stationDtoList.get(2).getId());
        }
    }

    @Nested
    @DisplayName("지하철 구간 삭제 테스트")
    class deleteSection {
        
    }




}
