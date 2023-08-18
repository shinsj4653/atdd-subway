package kuit.subway.dto.response.section;

import kuit.subway.dto.response.line.LineDto;
import lombok.Data;

import java.util.List;

@Data
public class SectionCreateResponse {
    private Long id;
    private List<LineDto> lines;
}
