package kuit.subway.dto.response.station;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class StationDto {
    private Long id;
    private String name;

    @Builder
    public StationDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
