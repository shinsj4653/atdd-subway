package kuit.subway.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.dto.response.line.LineDto;
import kuit.subway.dto.response.station.StationDto;
import kuit.subway.exception.badrequest.section.create.*;
import kuit.subway.exception.badrequest.section.delete.InvalidSectionDeleteLastStationException;
import kuit.subway.exception.badrequest.section.delete.InvalidSectionDeleteOnlyTwoStationsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        // 구간을 처음 추가하는 경우는 validate 할 필요 X
        if (sections.size() == 0) {
            this.sections.add(section);
        } else {
            validateSectionCreateBothNotExist(section);
            validateSectionCreateBothExist(section);
            validateSectionCreateLengthLonger(section);
            if (sections.size() > 0) {
                // 구간 추가 시, 고려해야 할 예외사항 먼저 체크
                if (validateSectionCreateFirstStation(section)) {
                    // 새로운 역을 상행 종점으로 등록할 경우
                    this.sections.add(0, section);
                } else if (validateSectionCreateFinalStation(section)) {
                    // 새로운 역을 하행 종점으로 등록할 경우
                    this.sections.add(section);
                } else {
                    // 사이 삽입 : 4가지 경우 존재
                    // 1. 새로 삽입 상행이 기존의 상행에 존재 : found 이전 자리에 삽입 후, found 업데이트
                    // 1 2 <= found
                    // 1 3 <= new
                    // 2 5

                    // 2. 새로 삽입 상행이 기존의 하행에 존재 : found 다음 자리에 삽입 후, new 다음걸 업데이트
                    // 1 2 <= found
                    // 2 3 <= new
                    // 2 5

                    // 3. 새로 삽입 하행이 기존의 상행에 존재 : found 이전 자리에 삽입 후, new 이전걸 업데이트
                    // 1 2
                    // 3 2 <= new
                    // 2 5 <= found

                    // 4. 새로 삽입 하행이 기존의 하행에 존재 : found 다음 자리에 삽입 후, found 업데이트
                    // 1 2
                    // 3 5 <= new
                    // 2 5 <= found
                    // 상행역이 이미 존재하는 역인지, 혹은 하행역이 존재하는 역인지 판별
                    Boolean isUpExist = validateInsertBetween(section);

                    // 새로운 상행이 이미 존재하는 경우
                    if (isUpExist) {
                        // 1 2
                        // 2 3 <= new
                        // 2 5 <= found

                        // 사이에 끼울 경우, 각 기존 구간의 상행역 & 하행역을 신규 구간 정보로 잘 변경
                        Section findSection = this.sections.stream()
                                .filter(s -> s.getUpStation().equals(section.getUpStation()))
                                .findFirst().get();
                        System.out.println("findSection upStation" + findSection.getUpStation().getId());
                        System.out.println("findSection downStation" + findSection.getDownStation().getId());

                        int index = this.sections.indexOf(findSection);
                        System.out.println("index : " + index);
                        int findDistance = findSection.getDistance();
                        int newDistance = section.getDistance();

                        // 새롭게 추가할 상행역, 하행역
                        Station newUpStation = section.getUpStation();
                        Station newDownStation = section.getDownStation();

                        // 추가해줄 구간 생성
                        Section newSection = Section.createSection(findSection.getLine(), newUpStation, newDownStation, newDistance);
                        this.sections.add(index, newSection);

                        // 기존 구간 정보 갱신
                        findSection.updateSection(newDownStation, findSection.getDownStation(), findDistance - newDistance);

                    } else {
                        // 새로운 하행이 기존 하행으로 존재할 경우
                        // 1 2
                        // 2 5 <= found
                        // 3 5 <= new
                        Section findSection = this.sections.stream()
                                .filter(s -> s.getDownStation().equals(section.getDownStation()))
                                .findFirst().get();

                        int index = this.sections.indexOf(findSection);
                        int findDistance = findSection.getDistance();
                        int newDistance = section.getDistance();

                        // 새롭게 추가할 상행역, 하행역
                        Station newUpStation = section.getUpStation();
                        Station newDownStation = section.getDownStation();

                        // 추가해줄 구간 생성
                        Section newSection = Section.createSection(findSection.getLine(), newUpStation, newDownStation, newDistance);
                        this.sections.add(index + 1, newSection);

                        // 기존 구간 정보 갱신
                        findSection.updateSection(findSection.getUpStation(), newUpStation, findDistance - newDistance);
                    }
                }
            }
        }
    }

    public List<StationDto> getStationDtoList() {
        List<StationDto> result = new ArrayList<>();
        Long nextUpStationId;

        // 맨 처음 첫 구간은 상행, 하행 둘 다 삽입
        Station upStation = sections.get(0).getUpStation();
        result.add(StationDto.createStationDto(upStation.getId(), upStation.getName()));

        Station downStation = sections.get(0).getDownStation();
        result.add(StationDto.createStationDto(downStation.getId(), downStation.getName()));

        nextUpStationId = downStation.getId();

        for (int i = 1; i < sections.size(); i++) {
            Long finalNextUpStationId = nextUpStationId;
            Section findSection = sections.stream()
                    .filter(section -> section.getUpStation().getId() == finalNextUpStationId)
                    .findFirst().get();
            System.out.println(findSection.getDownStation().getId());
            downStation = findSection.getDownStation();
            result.add(StationDto.createStationDto(downStation.getId(), downStation.getName()));
            nextUpStationId = downStation.getId();
        }

        return result;
    }

    // 구간이 1개일 경우 그 구간을 update 해주는 함수
    public void updateSections(Station upStation, Station downStation) {
        this.sections.get(0).updateStations(upStation, downStation);
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
    private boolean validateSectionCreateFirstStation(Section section) {
        Section firstSection = sections.get(0);
        if(firstSection.getUpStation().equals(section.getDownStation())) {
            return true;
        } else {
            return false;
        }
    }

    // 새로운 구간의 상행역이 등록되어있는 하행 종점역이면, 새로운 역을 하행 종점으로 등록할 경우
    private boolean validateSectionCreateFinalStation(Section section) {
        Section lastSection = sections.get(sections.size() - 1);
        if(lastSection.getDownStation().equals(section.getUpStation())) {
            return true;
        } else {
            return false;
        }
    }

    // 상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가 불가
    private void validateSectionCreateBothNotExist(Section section) {
        boolean upStationExist = sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation().equals(section.getUpStation()) || existedSection.getDownStation().equals(section.getUpStation()));
        boolean downStationExist =sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation().equals(section.getDownStation()) || existedSection.getDownStation().equals(section.getDownStation()));
        if (!upStationExist && !downStationExist) {
            throw new InvalidSectionCreateBothNotExistExcpetion();
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
        Optional<Section> upExist = this.sections.stream()
                .filter(s -> s.getUpStation().equals(section.getUpStation()))
                .findAny();

        Optional<Section> downExist = this.sections.stream()
                .filter(s -> s.getDownStation().equals(section.getDownStation()))
                .findAny();


        if ((upExist.isPresent() && upExist.get().getDistance() <= section.getDistance()) ||
                (downExist.isPresent() && downExist.get().getDistance() <= section.getDistance())) {
            throw new InvalidSectionCreateLengthLongerException();
        }
    }

    // 사이 삽입 : 4가지 경우 존재
    // 1. 새로 삽입 상행이 기존의 상행에 존재 : found 이전 자리에 삽입 후, found 업데이트
    // 1 2 <= found
    // 1 3 <= new
    // 2 5

    // 2. 새로 삽입 상행이 기존의 하행에 존재 : found 다음 자리에 삽입 후, new 다음걸 업데이트
    // 1 2 <= found
    // 2 3 <= new
    // 2 5

    // 3. 새로 삽입 하행이 기존의 상행에 존재 : found 이전 자리에 삽입 후, new 이전걸 업데이트
    // 1 2
    // 3 2 <= new
    // 2 5 <= found

    // 4. 새로 삽입 하행이 기존의 하행에 존재 : found 다음 자리에 삽입 후, found 업데이트
    // 1 2
    // 3 5 <= new
    // 2 5 <= found

    // 역 사이에 새로운 역 등록할 경우, 상행역, 혹은 하행역 중 어느 쪽이 이미 존재하는지 판별
    private Boolean validateInsertBetween(Section section) {
        Optional<Section> existUp = this.sections.stream()
                .filter(s -> s.getUpStation().equals(section.getUpStation()))
                .findAny();

        if (existUp.isPresent()) {
            return true;
        } else
            return false;
    }

    // 지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다. 즉, 마지막 구간만 제거할 수 있다.
    private void validateSectionDeleteLastStation(Station downStation) {
        if(!sections.get(sections.size() - 1).getDownStation().equals(downStation)){
            throw new InvalidSectionDeleteLastStationException();
        }
    }


}
