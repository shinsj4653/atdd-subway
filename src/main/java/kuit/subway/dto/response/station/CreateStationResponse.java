package kuit.subway.dto.response.station;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateStationResponse {
    private String message;
    private Long id;
}
