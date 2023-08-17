package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.dto.BaseTimeEntity;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
public class Station extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Builder
    public Station(String name) {
        this.name = name;
    }
}
