package kuit.subway.dto.response.line;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LineDeleteResponse {
    private String message;
    private Long id;
}
