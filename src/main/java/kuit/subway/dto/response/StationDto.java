package kuit.subway.dto.response;

import lombok.Data;

@Data
public class StationDto {
    private Long id;
    private String name;

    public StationDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
