package kuit.subway.exception.badrequest.auth;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_PW_ERROR;

@Getter
public class InvalidPasswordException extends BadRequestException {

    public InvalidPasswordException() {
        super(INVALID_PW_ERROR);
    }
}
