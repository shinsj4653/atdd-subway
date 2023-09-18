package kuit.subway.dto.response.line;

import kuit.subway.domain.Line;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LineDeleteResponse {
    private String message;
    private Long id;

    public static LineDeleteResponse of(Line line) {
        return LineDeleteResponse.builder()
                .id(line.getId())
                .message("지하철 노선 삭제 완료")
                .build();
    }
}
