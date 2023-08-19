package kuit.subway.dto.request.line;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateLineRequest {

    private String color;
    private int distance;
    private String name;
    private Long downStationId;
    private Long upStationId;
}
