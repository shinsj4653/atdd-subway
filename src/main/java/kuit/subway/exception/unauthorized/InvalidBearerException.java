package kuit.subway.exception.unauthorized;

import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_BEARER_ERROR;

@Getter
public class InvalidBearerException extends UnauthorizedException {
    public InvalidBearerException() {
        super(INVALID_BEARER_ERROR);
    }
}
