package kuit.subway.dto.response.line;

import kuit.subway.domain.Station;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class LineDto {
    private Long id;
    private String name;
    private String color;
    private List<Station> stations;

}
