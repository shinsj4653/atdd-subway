//package kuit.subway.service;
//
//import kuit.subway.domain.Line;
//import kuit.subway.domain.Station;
//import kuit.subway.dto.request.section.SectionCreateRequest;
//import kuit.subway.dto.request.section.SectionDeleteRequest;
//import kuit.subway.dto.response.line.LineDto;
//import kuit.subway.dto.response.section.SectionCreateResponse;
//import kuit.subway.dto.response.station.StationDto;
//import kuit.subway.exception.badrequest.*;
//import kuit.subway.exception.notfound.NotFoundLineException;
//import kuit.subway.exception.notfound.NotFoundStationException;
//import kuit.subway.repository.LineRepository;
//import kuit.subway.repository.StationRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class SectionService {
//    private final LineRepository lineRepository;
//    private final StationRepository stationRepository;
//
//    @Transactional
//    public LineDto addSection(Long lineId, SectionCreateRequest req) {
//
//        Long upStationId = req.getUpStationId();
//        Long downStationId = req.getDownStationId();
//
//        // 역에 대한 예외처리
//        validateStationExist(upStationId);
//        validateStationExist(downStationId);
//        validateSameStation(upStationId, downStationId);
//
//        // 노선에 대한 예외처리
//        Line line = validateLineExist(lineId);
//
//        List<Station> stations = line.getStations();
//
//        // 새로운 구간의 상행역은 해당 노선에 등록되어 있는 하행 종점역이어야 한다.
//        // 새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.
//        if (!validateNewUpStation(upStationId, stations)) {
//            throw new InvalidSectionCreateUpStationException();
//        } else if(!validateNewDownStation(downStationId, stations)) {
//            throw new InvalidSectionCreateDownStationException();
//        } else {
//            line.addStation(stationRepository.findById(downStationId).get());
//        }
//
//        LineDto result = LineDto.builder()
//                .id(line.getId())
//                .name(line.getName())
//                .color(line.getColor())
//                .stations(createStationDtoList(line.getStations()))
//                .createdDate(line.getCreatedDate())
//                .modifiedDate(LocalDateTime.now())
//                .build();
//
//        return result;
//    }
//
//    @Transactional
//    public LineDto deleteSection(Long lineId, SectionDeleteRequest req) {
//
//        Long deleteStationId = req.getDeleteStationId();
//
//        // 역에 대한 예외처리
//        validateStationExist(deleteStationId);
//
//        // 노선에 대한 예외처리
//        Line line = validateLineExist(lineId);
//
//        List<Station> stations = line.getStations();
//
//        // 지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다.
//        // 지하철 노선에 상행 종점역과 하행 종점역만 있는 경우(구간이 1개인 경우) 역을 삭제할 수 없다.
//        if (!validateLastDownStation(deleteStationId, stations)) {
//            throw new InvalidSectionDeleteLastStationException();
//        } else if(validateOnlyTwoStations(deleteStationId, stations)) {
//            throw new InvalidSectionDeleteOnlyTwoStationsException();
//        } else {
//            line.deleteStation(stationRepository.findById(deleteStationId).get());
//        }
//
//        LineDto result = LineDto.builder()
//                .id(line.getId())
//                .name(line.getName())
//                .color(line.getColor())
//                .stations(createStationDtoList(line.getStations()))
//                .createdDate(line.getCreatedDate())
//                .modifiedDate(LocalDateTime.now())
//                .build();
//
//        return result;
//    }
//
//    // 노선의 역 리스트 생성 함수
//    private List<StationDto> createStationDtoList(List<Station> stations) {
//
//        List<StationDto> result = stations.stream()
//                .map(station -> createStationDto(station)).collect(Collectors.toList());
//        return result;
//    }
//
//    // Station에서 StationDto로 변환해주는 함수
//    private StationDto createStationDto(Station station) {
//        return StationDto.builder()
//                .id(station.getId())
//                .name(station.getName())
//                .createdDate(station.getCreatedDate())
//                .modifiedDate(station.getModifiedDate()).build();
//    }
//
//    // 존재하는 역인지 판별해주는 함수
//    private Station validateStationExist(Long id) {
//        return stationRepository.findById(id)
//                .orElseThrow(NotFoundStationException::new);
//    }
//
//    // 노선으로 등록할 두 역이 같은 역인지 판별해주는 함수
//    private void validateSameStation(Long downStationId, Long upStationId) {
//        if (downStationId == upStationId) {
//            throw new InvalidLineStationException();
//        }
//    }
//
//    // 존재하는 노선인지 판별해주는 함수
//    private Line validateLineExist(Long id) {
//        return lineRepository.findById(id)
//                .orElseThrow(NotFoundLineException::new);
//    }
//
//    // 새로운 구간의 상행역은 해당 노선에 등록되어 있는 하행 종점역이어야 한다.
//    private Boolean validateNewUpStation(Long upStationId, List<Station> stations) {
//        return upStationId == stations.get(1).getId();
//    }
//
//    // 새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.
//    private Boolean validateNewDownStation(Long downStationId, List<Station> stations) {
//
//        for (Station station : stations) {
//            if (station.getId() == downStationId) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    // 지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다.
//    private Boolean validateLastDownStation(Long deleteStationId, List<Station> stations) {
//        return stations.get(stations.size() - 1).getId() == deleteStationId;
//    }
//
//    // 지하철 노선에 상행 종점역과 하행 종점역만 있는 경우(구간이 1개인 경우) 역을 삭제할 수 없다.
//    private Boolean validateOnlyTwoStations(Long deleteStationId, List<Station> stations) {
//        return stations.size() == 2;
//    }
//
//}
