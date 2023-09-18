package kuit.subway.repository;

import kuit.subway.domain.Line;
import kuit.subway.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long>  {

    List<Section> findByUpStationIdOrDownStationId(Long upStationId, Long downStationId);
}
