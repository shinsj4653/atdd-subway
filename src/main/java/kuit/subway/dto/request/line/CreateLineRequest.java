package kuit.subway.dto.request.line;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLineRequest {

    private String color;
    private int distance;
    private String name;
    private Long downStationId;
    private Long upStationId;
}
