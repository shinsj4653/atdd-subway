package kuit.subway.dto.response.line;

import kuit.subway.dto.response.station.StationReadResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class PathReadResponse {
    @Builder.Default
    private List<StationReadResponse> stations = new ArrayList<>();
    private double totalDistance;

    public static PathReadResponse of(List<StationReadResponse> stations, double totalDistance) {
        return PathReadResponse.builder()
                .stations(stations)
                .totalDistance(totalDistance)
                .build();
    }
}
