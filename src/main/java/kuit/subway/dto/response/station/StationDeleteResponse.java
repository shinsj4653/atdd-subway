package kuit.subway.dto.response.station;

import kuit.subway.domain.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StationDeleteResponse {
    private String message;
    private Long id;

    public static StationDeleteResponse of(Station station){
        return StationDeleteResponse.builder()
                .id(station.getId())
                .message("지하철 역 삭제 완료")
                .build();
    }
}
