package kuit.subway.dto.response.section;

import kuit.subway.domain.Station;
import kuit.subway.dto.response.line.LineDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SectionDto {
    private Long id;
    private String name;
    private List<LineDto> lines;

    @Builder
    public SectionDto(Long id, String name, List<LineDto> lines) {
        this.id = id;
        this.name = name;
        this.lines = lines;
    }
}
