package kuit.subway.dto.response.section;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SectionCreateResponse {
    private String message;
    private Long id;
}
