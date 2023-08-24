package kuit.subway.study.section;

import jakarta.transaction.Transactional;
import kuit.subway.repository.LineRepository;
import kuit.subway.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class SectionServiceTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    

}
