package kuit.subway.dto.response.line;

import kuit.subway.domain.Line;
import kuit.subway.domain.Station;
import kuit.subway.dto.response.station.StationReadResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
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
                .stations(getStationDtos(line.getStations()))
                .distance(line.getDistance())
                .build();
    }

    private static List<StationReadResponse> getStationDtos(List<Station> stations) {
        return stations.stream()
                .map(StationReadResponse::of)
                .collect(Collectors.toList());
    }
}
