package kuit.subway.dto.response.section;

import kuit.subway.dto.response.station.StationDto;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SectionDto {
    private Long id;
    private StationDto upStation;
    private StationDto downStation;

    @Builder
    public static SectionDto createSectionDto(Long id, StationDto upStation, StationDto downStation) {
        return SectionDto.builder()
                .id(id)
                .upStation(upStation)
                .downStation(downStation)
                .build();
    }
}
