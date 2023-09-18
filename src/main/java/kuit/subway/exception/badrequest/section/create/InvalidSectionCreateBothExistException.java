package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_SECTION_CREATE_BOTH_EXIST;

@Getter
public class InvalidSectionCreateBothExistException extends BadRequestException {

    public InvalidSectionCreateBothExistException() {
        super(INVALID_SECTION_CREATE_BOTH_EXIST);
    }
}
