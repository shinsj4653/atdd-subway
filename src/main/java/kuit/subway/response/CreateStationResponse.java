package kuit.subway.response;

import lombok.Data;

@Data
public class CreateStationResponse {
    private Long id;
    public CreateStationResponse(Long id) {
        this.id = id;
    }
}
