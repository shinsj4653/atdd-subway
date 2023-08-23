package kuit.subway.dto.request.line;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LineUpdateRequest {

    private String name;
    private String color;
    private int distance;
    private Long upStationId;
    private Long downStationId;
}