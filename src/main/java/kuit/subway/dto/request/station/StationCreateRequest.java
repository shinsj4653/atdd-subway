package kuit.subway.dto.request.station;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StationCreateRequest {
    private String name;
}