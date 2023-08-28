package kuit.subway.dto.response.line;

import kuit.subway.domain.Line;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LineCreateResponse {
    private String message;
    private Long id;

    public static LineCreateResponse of(Line line) {
        return LineCreateResponse.builder()
                .id(line.getId())
                .message("지하철 노선 생성 완료")
                .build();
    }
}
