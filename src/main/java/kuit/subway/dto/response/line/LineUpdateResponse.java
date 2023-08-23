package kuit.subway.dto.response.line;

import kuit.subway.dto.response.station.StationDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LineUpdateResponse {
    private Long id;
    private String name;
    private String color;
    private int distance;
    private List<StationDto> stations;

    public static LineUpdateResponse createLineUpdateResponse(Long id, String name, String color, int distance, List<StationDto> stations) {
        return LineUpdateResponse.builder()
                .id(id)
                .name(name)
                .color(color)
                .distance(distance)
                .stations(stations)
                .build();
    }
}
