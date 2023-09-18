package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.dto.BaseTimeEntity;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Line extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    private String name;

    private String color;

    private int distance;

    @Embedded
    @Builder.Default
    private Sections sections = new Sections();

    public static Line createLine(String name, String color, int distance) {
        return Line.builder()
                .name(name)
                .color(color)
                .distance(distance)
                .build();
    }

    public static Line createLineWithId(Long id, String name, String color, int distance) {
        return Line.builder()
                .id(id)
                .name(name)
                .color(color)
                .distance(distance)
                .build();
    }
    // 연관관계 메서드
    public void addSection(Section section) {
        this.sections.addSection(section);
    }

    public Section getFirstSection() {
        return this.sections.getFirstSection();
    }

    public void deleteSection(Station deleteStation) {
        this.sections.deleteSection(deleteStation);
    }
    public void updateLine(String name, String color, int distance) {
        this.name = name;
        this.color = color;
        this.distance = distance;
    }

    public List<Section> getSections() {
        return this.sections.getSections();
    }

    public List<Station> getStations() {
        return this.sections.getOrderStations();
    }

}
