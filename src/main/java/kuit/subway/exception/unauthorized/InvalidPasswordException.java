package kuit.subway.exception.unauthorized;

import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_PW_ERROR;

@Getter
public class InvalidPasswordException extends UnauthorizedException {

    public InvalidPasswordException() {
        super(INVALID_PW_ERROR);
    }
}
