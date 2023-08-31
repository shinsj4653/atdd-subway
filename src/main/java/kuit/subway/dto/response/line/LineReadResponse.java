package kuit.subway.dto.response.line;

import kuit.subway.domain.Line;
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

    public static LineReadResponse of(Line line) {
        return LineReadResponse.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .stations(line.getStationReadResponseList())
                .distance(line.getDistance())
                .build();
    }
}
