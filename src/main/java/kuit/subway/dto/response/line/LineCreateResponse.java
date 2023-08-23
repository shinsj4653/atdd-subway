package kuit.subway.dto.response.line;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LineCreateResponse {
    private String message;
    private Long id;
}
