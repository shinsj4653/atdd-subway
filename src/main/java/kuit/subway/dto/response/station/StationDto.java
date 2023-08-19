package kuit.subway.dto.response.station;

import kuit.subway.dto.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class StationDto extends BaseTimeEntity {
    private Long id;
    private String name;

    @Builder
    public StationDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
