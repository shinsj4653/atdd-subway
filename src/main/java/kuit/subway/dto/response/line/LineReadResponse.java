package kuit.subway.dto.response.line;

import kuit.subway.dto.response.station.StationReadResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class LineReadResponse {
    private Long id;
    private String name;
    private String color;
    private int distance;

    @Builder.Default
    private List<StationReadResponse> stations = new ArrayList<>();

    public static LineReadResponse createLineDto(Long id, String name, String color, int distance) {
        return LineReadResponse.builder()
                .id(id)
                .name(name)
                .color(color)
                .distance(distance)
                .build();
    }

    public void addStationDto(StationReadResponse stationReadResponse) {
        this.stations.add(stationReadResponse);
    }
}
