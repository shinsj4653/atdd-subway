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
    private int totalDistance;

    public static PathReadResponse createPathFindResponse(int totalDistance) {
        return PathReadResponse.builder()
                .totalDistance(totalDistance)
                .build();
    }

    public void addStationDto(StationReadResponse dto) {
        this.stations.add(dto);
    }
}
