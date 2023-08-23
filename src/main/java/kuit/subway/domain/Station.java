package kuit.subway.domain;

import jakarta.persistence.*;
import kuit.subway.dto.BaseTimeEntity;
import kuit.subway.dto.response.station.StationDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

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

    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "section_id")
    private List<Section> sections;
    public static Station createStation(String name, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        return Station.builder()
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
