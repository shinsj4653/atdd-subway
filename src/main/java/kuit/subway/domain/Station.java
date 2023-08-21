package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.dto.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Station extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "section_id")
    private List<Section> sections;

    @Builder
    public Station(String name, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        this.name = name;
    }

//    public void addLine(Line line) {
//        this.section = line;
//        line.getStations().add(this);
//    }
//
//    public void removeLine(Line line) {
//        this.line = null;
//    }
}
