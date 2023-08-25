package kuit.subway.dto.response.station;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Builder
public class StationDto {
    private Long id;
    private String name;

    public static StationDto createStationDto(Long id, String name) {
        return StationDto.builder()
                .id(id)
                .name(name)
                .build();
    }
}
