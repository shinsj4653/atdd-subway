package kuit.subway.exception.unauthorized;

import kuit.subway.exception.ExceptionContext;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_TOKEN_ERROR;

@Getter
public class InvalidTokenException extends UnauthorizedException {
    public InvalidTokenException() {
        super(INVALID_TOKEN_ERROR);
    }
}
