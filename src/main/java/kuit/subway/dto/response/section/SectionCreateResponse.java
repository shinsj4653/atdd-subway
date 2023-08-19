package kuit.subway.dto.response.section;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SectionCreateResponse {
    private String message;
    private Long id;
}
