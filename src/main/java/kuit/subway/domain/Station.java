package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.dto.BaseTimeEntity;
import lombok.*;

import java.util.Objects;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Station extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;


    public static Station createStation(String name) {
        return Station.builder()
                .name(name)
                .build();
    }

    public static Station createStationWithId(Long id, String name) {
        return Station.builder()
                .id(id)
                .name(name)
                .build();
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Station)) {
            return false;
        }
        Station station = (Station) o;
        return station.getId().equals(id) && station.getName().equals(name);
    }
}
