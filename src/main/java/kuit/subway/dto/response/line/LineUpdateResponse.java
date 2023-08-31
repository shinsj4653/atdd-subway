package kuit.subway.dto.response.line;

import kuit.subway.domain.Line;
import kuit.subway.dto.response.station.StationReadResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class LineUpdateResponse {
    private Long id;
    private String name;
    private String color;
    private int distance;

    @Builder.Default
    private List<StationReadResponse> stations = new ArrayList<>();

    public static LineUpdateResponse of(Line line) {
        return LineUpdateResponse.builder()
                .id(line.getId())
                .name(line.getName())
                .color(line.getColor())
                .distance(line.getDistance())
                .stations(line.getStationReadResponseList())
                .build();
    }
}
