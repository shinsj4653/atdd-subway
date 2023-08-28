package kuit.subway.dto.request.line;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LineCreateRequest {

    private String name;
    private String color;
    private int lineDistance;
    private Long upStationId;
    private Long downStationId;
    private int sectionDistance;
}
