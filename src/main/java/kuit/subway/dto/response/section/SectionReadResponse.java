package kuit.subway.dto.response.section;

import kuit.subway.dto.response.station.StationReadResponse;
import lombok.*;

@Getter
@Builder
public class SectionReadResponse {
    private Long id;
    private StationReadResponse upStation;
    private StationReadResponse downStation;

    public static SectionReadResponse createSectionDto(Long id, StationReadResponse upStation, StationReadResponse downStation) {
        return SectionReadResponse.builder()
                .id(id)
                .upStation(upStation)
                .downStation(downStation)
                .build();
    }
}
