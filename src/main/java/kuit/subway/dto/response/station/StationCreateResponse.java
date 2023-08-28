package kuit.subway.dto.response.station;

import kuit.subway.domain.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StationCreateResponse {
    private String message;
    private Long id;

    public static StationCreateResponse of(Station station) {
        return StationCreateResponse.builder()
                .id(station.getId())
                .message("지하철 역 추가 완료")
                .build();
    }
}
