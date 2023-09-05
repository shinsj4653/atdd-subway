package kuit.subway.exception.unauthorized;

import kuit.subway.exception.ExceptionContext;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.WRONG_EMAIL_ERROR;

@Getter
public class WrongEmailException extends UnauthorizedException {
    public WrongEmailException() {
        super(WRONG_EMAIL_ERROR);
    }
}
