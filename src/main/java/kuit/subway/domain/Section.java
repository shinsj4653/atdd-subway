package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.dto.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Section extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    @Builder
    public Section(LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
    }

    // 연관관계 메서드
    public void addStations(Station upStation, Station downStation) {
        this.upStation = upStation;
        this.downStation = downStation;
        upStation.getSections().add(this);
        downStation.getSections().add(this);
    }

    public void addLine(Line line) {
        this.line = line;
    }

}
