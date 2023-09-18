package kuit.subway.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateBothExistException;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateBothNotExistExcpetion;
import kuit.subway.exception.badrequest.section.create.InvalidSectionCreateLengthLongerException;
import kuit.subway.exception.badrequest.section.delete.InvalidSectionDeleteOnlyTwoStationsException;
import kuit.subway.exception.badrequest.section.delete.InvalidSectionDeleteStationNotExist;
import kuit.subway.exception.notfound.section.NotFoundSectionHavingCycleException;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        // 구간을 처음 추가하는 경우는 validate 할 필요 X
        if (sections.size() == 0) {
            this.sections.add(section);
            return;
        }

        validateSectionCreateBothNotExist(section);
        validateSectionCreateBothExist(section);
        validateSectionCreateLengthLonger(section);

        // 구간 추가 시, 고려해야 할 예외사항 먼저 체크
        if (validateSectionCreateFirstStation(section)) {
            // 새로운 역을 상행 종점으로 등록할 경우
            this.sections.add(0, section);
            return;
        }
        if (validateSectionCreateFinalStation(section)) {
            // 새로운 역을 하행 종점으로 등록할 경우
            this.sections.add(section);
            return;
        }
        // 상행역이 이미 존재하는 역인지, 혹은 하행역이 존재하는 역인지 판별
        // 새로운 상행이 이미 존재하는 경우
        // 1 2
        // 2 3 <= new
        // 2 5 <= found
        // 새로운 하행이 기존 하행으로 존재할 경우
        // 1 2
        // 2 5 <= found
        // 3 5 <= new
        insertSectionBetweenSections(section);
    }

    private void insertSectionBetweenSections(Section requestSection) {

        Optional<Section> upSectionOptional = findMatchUpSection(requestSection.getUpStation());
        Optional<Section> downSectionOptional = findMatchDownSection(requestSection.getDownStation());

        Section findSection;

        if (upSectionOptional.isPresent()) {
            findSection = upSectionOptional.get();
        } else {
            findSection = downSectionOptional.get();
        }

        int findDistance = findSection.getDistance();
        int newDistance = requestSection.getDistance();

        // 새롭게 추가할 상행역, 하행역
        Station newUpStation = requestSection.getUpStation();
        Station newDownStation = requestSection.getDownStation();

        // 새로운 구간 추가
        Section newSection = Section.createSection(findSection.getLine(), newUpStation, newDownStation, newDistance);
        sections.add(newSection);

        // 기존에 존재한 구간 정보 갱신
        if (upSectionOptional.isPresent()) {
            findSection.updateSection(newDownStation, findSection.getDownStation(), findDistance - newDistance);
        } else {
            findSection.updateSection(findSection.getUpStation(), newUpStation, findDistance - newDistance);
        }
    }

    // 구간들 상행 종점역 기준으로 정렬한 후, 정렬된 역 리스트를 반환해주는 함수
    public List<Station> getOrderStations() {
        Section startSection = findStartSection();
        List<Section> orderedSections = new ArrayList<>();

        Map<Station, Section> upStationAndSectionRoute = getSectionRoute();
        Section nextSection = startSection;

        while (nextSection != null) {
            orderedSections.add(nextSection);
            Station curDownStation = nextSection.getDownStation();
            nextSection = upStationAndSectionRoute.get(curDownStation);
        }
        return getStations(orderedSections);
    }

    private Section findStartSection() {
        Set<Station> downStations = sections.stream()
                .map(Section::getDownStation)
                .collect(Collectors.toSet());
        // 전체 상행역 중 하행역이 아닌 상행역 추출(=> 시작점)
        return sections.stream()
                .filter(section -> !downStations.contains(section.getUpStation()))
                .findFirst()
                .orElseThrow(() -> new NotFoundSectionHavingCycleException());
    }

    private Map<Station, Section> getSectionRoute() {
        return sections.stream()
                .collect(
                        Collectors.toMap(Section::getUpStation,
                                section -> section,
                                (stationKey1, stationKey2) -> stationKey1,
                                HashMap::new));
    }

    public Section getFirstSection() {
        return sections.get(0);
    }

    public void deleteSection(Station deleteStation) {

        if (sections.size() > 1) {
            validateSectionDeleteStationNotExist(deleteStation);

            if (isLastStationDelete(deleteStation)) {
                // 하행 종점 제거
                this.sections.remove(sections.size() - 1);
                return;
            }
            if (isFirstStationDelete(deleteStation)){
                // 상행 종점 제거
                this.sections.remove(0);
                return;
            }
            // 중간역 제거
            // A B
            // B C
            Section findSection = findMatchDownSection(deleteStation).get();
                
            // 거리 계산을 위해 다음 구간 가져오기
            Section nextSection = findMatchUpSection(deleteStation).get();
                
            // 거리 계산까지 하여 findSection 업데이트
            findSection.updateSection(findSection.getUpStation(), nextSection.getDownStation(), findSection.getDistance() + nextSection.getDistance());

            // 업데이트 구간의 다음 구간 삭제
            this.sections.remove(nextSection);
            return;
        }
        // 구간이 하나인 노선에서 구간 제거 불가
        throw new InvalidSectionDeleteOnlyTwoStationsException();

    }

    private List<Station> getStations(List<Section> sections) {

        List<Station> result = new ArrayList<>();

        // 맨 처음 첫 구간은 상행, 하행 둘 다 삽입
        Station upStation = sections.get(0).getUpStation();
        Station downStation = sections.get(0).getDownStation();

        result.add(upStation);
        result.add(downStation);

        Long nextUpStationId = downStation.getId();

        for (int i = 0; i < sections.size() - 1; i++) {
            Long finalNextUpStationId = nextUpStationId;
            Section findSection = sections.stream()
                    .filter(section -> section.getUpStation().getId().equals(finalNextUpStationId))
                    .findFirst().get();
            downStation = findSection.getDownStation();
            result.add(downStation);
            nextUpStationId = downStation.getId();
        }
        return result;
    }


    // 주어진 역을 이미 상행역으로 가지고 있는 구간 반환
    private Optional<Section> findMatchUpSection(Station upStation) {
        return sections.stream()
                .filter(s -> s.getUpStation().equals(upStation))
                .findFirst();
    }

    // 주어진 역을 이미 하행역으로 가지고 있는 구간 반환
    private Optional<Section> findMatchDownSection(Station downStation) {
        return sections.stream()
                .filter(s -> s.getDownStation().equals(downStation))
                .findFirst();
    }

    // 새로운 구간의 상행역이 등록되어있는 하행 종점역이면, 새로운 역을 하행 종점으로 등록할 경우
    private Boolean validateSectionCreateFirstStation(Section section) {
        Section firstSection = sections.get(0);
        if(firstSection.getUpStation().equals(section.getDownStation())) {
            return true;
        } else {
            return false;
        }
    }

    // 새로운 구간의 상행역이 등록되어있는 하행 종점역이면, 새로운 역을 하행 종점으로 등록할 경우
    private Boolean validateSectionCreateFinalStation(Section section) {
        Station lastStation = getOrderStations().get(this.sections.size());
        if(lastStation.equals(section.getUpStation())) {
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

    // 노선에 등록되어 있지 않은 역은 제거 불가
    private void validateSectionDeleteStationNotExist(Station deleteStation) {
        if (findMatchUpSection(deleteStation).isEmpty() && findMatchDownSection(deleteStation).isEmpty())
            throw new InvalidSectionDeleteStationNotExist();
    }

    // 하행 종점 제거인지 아닌지 판별해주는 함수
    private Boolean isLastStationDelete(Station deleteStation) {
        return this.sections.get(sections.size() - 1).getDownStation().equals(deleteStation);
    }

    // 상행 종점 제거인지 아닌지 판별해주는 함수
    private Boolean isFirstStationDelete(Station deleteStation) {
        return this.sections.get(0).getUpStation().equals(deleteStation);
    }

}
