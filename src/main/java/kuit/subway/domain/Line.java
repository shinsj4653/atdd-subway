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
@NoArgsConstructor
public class Line extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    private String color;

    private int distance;

    private String name;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
    private List<Station> stations = new ArrayList<>();

    @Builder
    public Line(String color, int distance, String name, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        this.color = color;
        this.distance = distance;
        this.name = name;
    }

    // 연관관계 메서드
    public void addStations(List<Station> stations) {
        stations.forEach(station -> station.addLine(this));
    }

    public void updateLine(String color, int distance, String name, LocalDateTime modifiedDate) {
        this.color = color;
        this.distance = distance;
        this.name = name;
        this.setModifiedDate(modifiedDate);
    }

    public void updateStations(List<Station> stations) {
        // Remove the existing stations from the line's list
        this.stations.forEach(station -> station.removeLine(this));
        // Clear the existing stations list
        this.stations.clear();

        this.stations = new ArrayList<>(stations);
        addStations(stations);
    }

}
