package kuit.subway.dto.response.station;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StationDeleteResponse {
    private String message;
    private Long id;
}
