package kuit.subway.dto.response.line;

import kuit.subway.domain.Station;
import kuit.subway.dto.BaseTimeEntity;
import kuit.subway.dto.response.section.SectionDto;
import kuit.subway.dto.response.station.StationDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LineDto {
    private Long id;
    private String name;
    private String color;
    private int distance;
    private List<StationDto> stations = new ArrayList<>();

    public static LineDto createLineDto(Long id, String name, String color, int distance) {
        return LineDto.builder()
                .id(id)
                .name(name)
                .color(color)
                .distance(distance)
                .build();
    }

    public void addStationDto(StationDto stationDto) {
        this.stations.add(stationDto);
    }
}
