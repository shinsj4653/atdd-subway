package kuit.subway.exception.notfound.line;

import kuit.subway.exception.notfound.NotFoundException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.NOT_FOUND_LINE_ERROR;

@Getter
public class NotFoundLineException extends NotFoundException {
    public NotFoundLineException() {
        super(NOT_FOUND_LINE_ERROR);
    }
}
