package kuit.subway.dto.response.section;

import kuit.subway.domain.Line;
import kuit.subway.dto.response.station.StationReadResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class SectionDeleteResponse {
    private Long id;
    private String message;
    private String name;
    private String color;

    @Builder.Default
    private List<StationReadResponse> stations = new ArrayList<>();

    public static SectionDeleteResponse of(Line line) {
        return SectionDeleteResponse.builder()
                .id(line.getId())
                .message("지하철 구간 삭제 완료")
                .name(line.getName())
                .color(line.getColor())
                .stations(line.getSections().getOrderStations())
                .build();
    }
}
