package kuit.subway.dto.request.section;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SectionCreateRequest {

    private Long upStationId;
    private Long downStationId;
    private int distance;

}
