package kuit.subway.dto.response.line;

import kuit.subway.dto.response.station.StationDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class PathFindResponse {
    private List<StationDto> stations;
    private int totalDistance;

    public static PathFindResponse createPathFindResponse(int totalDistance) {
        return PathFindResponse.builder()
                .totalDistance(totalDistance)
                .build();
    }
}
