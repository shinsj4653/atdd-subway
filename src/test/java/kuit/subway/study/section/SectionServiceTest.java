package kuit.subway.study.section;

import jakarta.transaction.Transactional;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("지하철 구간 Classic 테스트")
@SpringBootTest
@Transactional
public class SectionServiceTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

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

    @DisplayName("지하철 구간 생성 - 새로운 역을 상행 종점으로 등록할 경우 맨 상위 구간으로 추가")
    @Test
    void addSectionFirstUpStation() {

        // given
        line.addSection(Section.createSection(line, station3, station1, 5));

        // when
        List<StationDto> stationDtoList = line.getSections().getStationDtoList();

        // then
        assertEquals(station3.getId(), stationDtoList.get(0).getId());

    }

    @DisplayName("지하철 구간 생성 - 새로운 역을 하행 종점으로 등록할 경우 맨 하위 구간으로 추가")
    @Test
    void addSectionLastDownStation() {

        // given
        line.addSection(Section.createSection(line, station2, station3, 5));

        // when
        List<StationDto> stationDtoList = line.getSections().getStationDtoList();

        // then
        assertEquals(station3.getId(), stationDtoList.get(stationDtoList.size() - 1).getId());

    }

    @DisplayName("사이에 끼울 경우 각 기존 구간의 상행역 or 하행역을 신규 구간 정보로 잘 변경 - 새로운 구간의 상행역이 이미 존재할 경우")
    @Test
    void addSectionBetweenStationsUpExist() {

        // given
        line.addSection(Section.createSection(line, station1, station3, 2));

        // when
        List<StationDto> stationDtoList = line.getSections().getStationDtoList();

        // then
        assertEquals(station3.getId(), stationDtoList.get(1).getId());
        assertEquals(3, stationDtoList.size());
    }

    @DisplayName("사이에 끼울 경우 각 기존 구간의 상행역 or 하행역을 신규 구간 정보로 잘 변경 - 새로운 구간의 하행역이 이미 존재할 경우")
    @Test
    void addSectionBetweenStationsDownExist() {

        // given
        line.addSection(Section.createSection(line, station3, station2, 2));

        // when
        List<StationDto> stationDtoList = line.getSections().getStationDtoList();

        // then
        assertEquals(station3.getId(), stationDtoList.get(1).getId());
        assertEquals(3, stationDtoList.size());
    }

    @DisplayName("노선 조회 시 상행 종점을 기준으로 역들이 잘 정렬되어 반환되어야 함")
    @Test
    void addSectionReturnAsSorted() {

        // given
        line.addSection(Section.createSection(line, station3, station2, 2));

        // when
        List<StationDto> stationDtoList = line.getSections().getStationDtoList();

        // then
        assertEquals(station1.getId(), stationDtoList.get(0).getId());
        assertEquals(station3.getId(), stationDtoList.get(1).getId());
        assertEquals(station2.getId(), stationDtoList.get(2).getId());
    }



}
