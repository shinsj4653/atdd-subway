package kuit.subway.dto.response.station;

import lombok.*;

@Getter
@Builder
public class StationReadResponse {
    private Long id;
    private String name;

    public static StationReadResponse createStationDto(Long id, String name) {
        return StationReadResponse.builder()
                .id(id)
                .name(name)
                .build();
    }
}
