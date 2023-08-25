package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_SECTION_CREATE_LENGTH_LONGER_ERROR;

@Getter
public class InvalidSectionCreateLengthLongerException extends BadRequestException {
    public InvalidSectionCreateLengthLongerException() {
        super(INVALID_SECTION_CREATE_LENGTH_LONGER_ERROR);
    }
}

