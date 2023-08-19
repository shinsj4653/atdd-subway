package kuit.subway.dto.response.line;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLineResponse {
    private String message;
    private Long id;
}
