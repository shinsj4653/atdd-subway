package kuit.subway.dto.response.line;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class LineUpdateResponse {
    private String color;
    private int distance;
    private String name;
    private Long downStationId;
    private Long upStationId;

    @Builder
    public LineUpdateResponse(String color, int distance, String name, Long downStationId, Long upStationId) {
        this.color = color;
        this.distance = distance;
        this.name = name;
        this.downStationId = downStationId;
        this.upStationId = upStationId;
    }
}
