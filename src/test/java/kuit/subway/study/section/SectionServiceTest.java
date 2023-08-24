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
@DisplayName("지하철 구간 Classic 테스트")
@SpringBootTest
@Transactional
public class SectionServiceTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;
    
    // 구간 "만" 추가, 혹은 삭제하는 상황을 테스트 해주기 위해 미리 노선까지는 생성해주기
    @BeforeEach
    void setUpLine() {
        
        Station upStation = Station.createStation("강남역");
        Station downStation = Station.createStation("수서역");
        stationRepository.save(upStation);
        stationRepository.save(downStation);
        
        Line line = Line.createLine("와우선", "green", 20);
        line.addSection(Section.createSection(line, upStation, downStation, 1));
        lineRepository.save(line);
    }

    @DisplayName("지하철 구간 생성 - 새로운 역을 상행 종점으로 등록할 경우 맨 상위 구간으로 추가")
    @Test
    void addSectionFirst() {
        
    }


}
