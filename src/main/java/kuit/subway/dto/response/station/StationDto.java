package kuit.subway.dto.response.station;

import kuit.subway.dto.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
public class StationDto extends BaseTimeEntity {
    private Long id;
    private String name;

    @Builder
    public StationDto(Long id, String name, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        this.id = id;
        this.name = name;
    }
}
