package kuit.subway.exception.badrequest.auth;

import kuit.subway.exception.ExceptionContext;
import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.BLANK_TOKEN_ERROR;

@Getter
public class BlankTokenException extends BadRequestException {
    public BlankTokenException() {
        super(BLANK_TOKEN_ERROR);
    }
}
