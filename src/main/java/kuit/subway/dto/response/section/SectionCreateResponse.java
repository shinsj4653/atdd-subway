package kuit.subway.dto.response.section;

import kuit.subway.domain.Line;
import kuit.subway.dto.response.line.LineReadResponse;
import kuit.subway.dto.response.station.StationReadResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SectionCreateResponse {
    private Long id;
    private String message;
    private String name;
    private String color;

    @Builder.Default
    private List<StationReadResponse> stations = new ArrayList<>();

    public static SectionCreateResponse of(Line line) {
        return SectionCreateResponse.builder()
                .id(line.getId())
                .message("지하철 구간 추가 완료")
                .name(line.getName())
                .color(line.getColor())
                .stations(line.getSections().getOrderStations())
                .build();
    }
}
