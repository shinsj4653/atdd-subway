package kuit.subway.study.section;

import jakarta.transaction.Transactional;
import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import kuit.subway.domain.Station;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
@DisplayName("지하철 구간 Classic 예외 테스트")
public class SectionServiceExceptionTest {

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

    @Test
    @DisplayName("상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가 불가")
    void StationsBothNotExist() {

    }

}
