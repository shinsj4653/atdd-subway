package kuit.subway.dto.response.line;

import kuit.subway.dto.response.station.StationDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PathFindResponse {
    private List<StationDto> stations;
    private int totalDistance;

    public static PathFindResponse createPathFindResponse(List<StationDto> stations, int totalDistance) {
        return PathFindResponse.builder()
                .stations(stations)
                .totalDistance(totalDistance)
                .build();
    }
}
