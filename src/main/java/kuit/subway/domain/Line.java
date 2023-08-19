package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.dto.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Line extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private Long id;

    private String color;

    private int distance;

    private String name;

    private Long downStationId;

    private Long upStationId;

    @Builder
    public Line(String color, int distance, String name, Long downStationId, Long upStationId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        this.color = color;
        this.distance = distance;
        this.name = name;
        this.downStationId = downStationId;
        this.upStationId = upStationId;
    }
}
