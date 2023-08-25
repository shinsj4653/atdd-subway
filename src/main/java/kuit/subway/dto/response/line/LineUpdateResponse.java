package kuit.subway.dto.response.line;

import kuit.subway.dto.response.station.StationDto;
import lombok.*;

import java.util.ArrayList;
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
    private List<StationDto> stations = new ArrayList<>();

    public static LineUpdateResponse createLineUpdateResponse(Long id, String name, String color, int distance) {
        return LineUpdateResponse.builder()
                .id(id)
                .name(name)
                .color(color)
                .distance(distance)
                .build();
    }

    public void addStationDto(StationDto dto) {
        this.stations.add(dto);
    }
}
