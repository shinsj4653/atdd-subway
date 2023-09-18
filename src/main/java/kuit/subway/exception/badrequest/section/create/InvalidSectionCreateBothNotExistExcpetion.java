package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_SECTION_CREATE_BOTH_NOT_EXIST;

@Getter
public class InvalidSectionCreateBothNotExistExcpetion extends BadRequestException {
    public InvalidSectionCreateBothNotExistExcpetion() {
        super(INVALID_SECTION_CREATE_BOTH_NOT_EXIST);
    }
}
