package kuit.subway.dto.response.line;

import kuit.subway.domain.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateLineResponse {
    private String color;
    private int distance;
    private String name;
    private Long downStationId;
    private Long upStationId;

    @Builder
    public UpdateLineResponse(String color, int distance, String name, Long downStationId, Long upStationId) {
        this.color = color;
        this.distance = distance;
        this.name = name;
        this.downStationId = downStationId;
        this.upStationId = upStationId;
    }
}
