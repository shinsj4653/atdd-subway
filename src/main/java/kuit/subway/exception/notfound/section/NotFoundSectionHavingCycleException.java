package kuit.subway.exception.notfound.section;

import kuit.subway.exception.notfound.NotFoundException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.NOT_FOUND_SECTION_HAVING_CYCLE_ERROR;

@Getter
public class NotFoundSectionHavingCycleException extends NotFoundException {
    public NotFoundSectionHavingCycleException() {
        super(NOT_FOUND_SECTION_HAVING_CYCLE_ERROR);
    }
}
