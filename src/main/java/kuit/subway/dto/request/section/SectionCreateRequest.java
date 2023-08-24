package kuit.subway.dto.request.section;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SectionCreateRequest {

    private Long upStationId;
    private Long downStationId;
    private int distance;

}
