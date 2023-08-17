package kuit.subway.dto.response.line;

import kuit.subway.domain.Station;
import kuit.subway.dto.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LineDto extends BaseTimeEntity {
    private Long id;
    private String name;
    private String color;
    private List<Station> stations;

    @Builder
    public LineDto(Long id, String name, String color, List<Station> stations) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.stations = stations;
    }
}
