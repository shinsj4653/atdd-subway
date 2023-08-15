package kuit.subway.service;

import kuit.subway.domain.Station;
import kuit.subway.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    @Transactional
    public Long addStation(Station station) {
        stationRepository.save(station);
        return station.getId();
    }

    @Transactional
    public List<Station> findStations() {
        return stationRepository.findAll();
    }

    @Transactional
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}
