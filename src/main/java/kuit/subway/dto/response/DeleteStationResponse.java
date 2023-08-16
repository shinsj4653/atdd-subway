package kuit.subway.dto.response;

import lombok.Data;

@Data
public class DeleteStationResponse {
    private Long id;
    public DeleteStationResponse(Long id) {
        this.id = id;
    }
}
