package kuit.subway.dto.response.station;

import kuit.subway.domain.Station;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class StationReadResponse {

    private Long id;
    private String name;

    public static StationReadResponse of(Station station){
        return StationReadResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .build();
    }
}
