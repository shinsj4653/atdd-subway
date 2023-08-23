package kuit.subway.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateBothExistException;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateDownStationException;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateLengthLongerException;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateUpStationException;
import kuit.subway.exception.badrequest.section.delete.InvalidSectionDeleteLastStationException;
import kuit.subway.exception.badrequest.section.delete.InvalidSectionDeleteOnlyTwoStationsException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        // 구간을 처음 추가하는 경우는 validate 할 필요 X
        if(sections.size() > 0) {
            //validateSectionCreateUpStation(section);
            //validateSectionCreateDownStation(section);
            validateSectionCreateBothNotExist(section);
            validateSectionCreateBothExist(section);
            validateSectionCreateLengthLonger(section);
        } else if (validateSectionCreateFinalStation(section)) {
            this.sections.add(section);
        } else if (validateSectionCreateFinalStation(section)) {
            this.sections.add(section);
        }
    }

    public List<StationDto> getStationDtoList() {
        List<StationDto> result = new ArrayList<>();
        for (Section section : sections) {
            if (result.size() == 0){
                Station upStation = section.getUpStation();
                Station downStation = section.getDownStation();
                result.add(StationDto.createStationDto(upStation.getId(), upStation.getName()));
                result.add(StationDto.createStationDto(downStation.getId(), downStation.getName()));
            } else {
                Station downStation = section.getDownStation();
                result.add(StationDto.createStationDto(downStation.getId(), downStation.getName()));
            }

        }
        return result;
    }

    public void updateSections(Station upStation, Station downStation) {
        this.sections.forEach(section -> {
            section.updateStations(upStation, downStation);
        });
    }

    public void deleteSection(Station station) {

        if (sections.size() > 1) {
            validateSectionDeleteLastStation(station);
        } else if (sections.size() == 1){
            throw new InvalidSectionDeleteOnlyTwoStationsException();
        }
        this.sections.removeIf(section -> (section.getDownStation().equals(station)));
    }

    // 새로운 구간의 상행역이 등록되어있는 하행 종점역이면, 새로운 역을 하행 종점으로 등록할 경우
    private boolean validateSectionCreateFinalStation(Section section) {
        Section lastSection = sections.get(sections.size() - 1);
        if(lastSection.getDownStation().equals(section.getUpStation())) {
            //throw new InvalidSectionCreateUpStationException();
            return true;
        }
    }

    // 새로운 구간의 상행역이 등록되어있는 하행 종점역이면, 새로운 역을 하행 종점으로 등록할 경우
    private boolean validateSectionCreateFinalStation(Section section) {
        Section lastSection = sections.get(sections.size() - 1);
        if(lastSection.getDownStation().equals(section.getUpStation())) {
            //throw new InvalidSectionCreateUpStationException();
            return true;
        }
    }

    // 새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.
//    private void validateSectionCreateDownStation(Section section) {
//        boolean isExist = sections.stream().anyMatch(existedSection ->
//                existedSection.getUpStation().equals(section.getDownStation()) || existedSection.getDownStation().equals(section.getDownStation()));
//        if (isExist) {
//            throw new InvalidSectionCreateDownStationException();
//        }
//
//    }

    // 상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가 불가
    private void validateSectionCreateBothNotExist(Section section) {
        boolean upStationExist = sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation().equals(section.getUpStation()) || existedSection.getDownStation().equals(section.getUpStation()));
        boolean downStationExist =sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation().equals(section.getDownStation()) || existedSection.getDownStation().equals(section.getDownStation()));
        if (!upStationExist && !downStationExist) {
            throw new InvalidSectionCreateBothExistException();
        }
    }

    // 상행역과 하행역이 이미 노선에 모두 등록되어 있다면 추가 불가
    private void validateSectionCreateBothExist(Section section) {
        boolean upStationExist = sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation().equals(section.getUpStation()) || existedSection.getDownStation().equals(section.getUpStation()));
        boolean downStationExist =sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation().equals(section.getDownStation()) || existedSection.getDownStation().equals(section.getDownStation()));
        if (upStationExist && downStationExist) {
            throw new InvalidSectionCreateBothExistException();
        }
    }

    // 역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크거나 같으면 추가 불가
    private void validateSectionCreateLengthLonger(Section section) {
        Optional<Section> findSection = this.sections.stream()
                .filter(s -> s.getUpStation().equals(section.getUpStation()))
                .findAny();

        if (findSection.isPresent() && findSection.get().getDistance() <= section.getDistance()) {
            throw new InvalidSectionCreateLengthLongerException();
        }
    }

    // 지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다. 즉, 마지막 구간만 제거할 수 있다.
    private void validateSectionDeleteLastStation(Station downStation) {
        if(!sections.get(sections.size() - 1).getDownStation().equals(downStation)){
            throw new InvalidSectionDeleteLastStationException();
        }
    }

}
