package kuit.subway.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kuit.subway.exception.badrequest.InvalidSectionCreateDownStationException;
import kuit.subway.exception.badrequest.InvalidSectionCreateUpStationException;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Section> sections = new ArrayList<>();

    public void addSection(Section section) {
        validateSectionCreateUpStation(section);
        validateSectionCreateDownStation(section);
        this.sections.add(section);
    }

    // 새로운 구간의 상행역은 등록되어있는 하행 종점역이어야 한다.
    private void validateSectionCreateUpStation(Section section) {
        Section lastSection = sections.get(sections.size() - 1);
        if(!lastSection.getDownStation().equals(section.getUpStation())) {
            throw new InvalidSectionCreateUpStationException();
        }
    }

    // 새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.
    private void validateSectionCreateDownStation(Section section) {
        boolean isExist = sections.stream().anyMatch(existedSection ->
                existedSection.getUpStation().equals(section.getDownStation()) || existedSection.getDownStation().equals(section.getDownStation()));
        if (isExist) {
            throw new InvalidSectionCreateDownStationException();
        }

    }

}
