package kuit.subway.dto.response.station;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StationCreateResponse {
    private String message;
    private Long id;
}
