package kuit.subway.dto.request.line;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PathFindRequest {

    private Long startStationId;
    private Long endStationid;
}
