package kuit.subway.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kuit.subway.dto.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jakarta.persistence.FetchType.*;

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

    private Sections sections;

    public static Line createLine(String name, String color, int distance) {
        return Line.builder()
                .name(name)
                .color(color)
                .distance(distance)
                .build();
    }
    // 연관관계 메서드
    public void addSection(Sections sections) {
        this.sections = sections;
    }
//
//    public void deleteStation(Station station) {
//        station.removeLine(this);
//    }
//    public void addStations(List<Station> stations) {
//        stations.forEach(station -> station.addLine(this));
//    }
//
    public void updateLine(String name, String color, int distance) {
        this.name = name;
        this.color = color;
        this.distance = distance;
    }
//
//    public void updateStations(List<Station> stations) {
//        // 현재 노선에 존재하고 있는 역들을 제거
//        this.stations.forEach(station -> station.removeLine(this));
//        // stations 리스트 clear()
//        this.stations.clear();
//
//        this.stations = new ArrayList<>(stations);
//        addStations(stations);
//    }

}
