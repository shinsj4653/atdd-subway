package kuit.subway.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @OneToOne(mappedBy = "station", fetch = LAZY)
    private Line line;

    public Station(String name) {
        this.name = name;
    }
}
