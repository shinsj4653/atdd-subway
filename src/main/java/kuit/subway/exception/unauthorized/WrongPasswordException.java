package kuit.subway.exception.unauthorized;

import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.WRONG_PW_ERROR;

@Getter
public class WrongPasswordException extends UnauthorizedException {

    public WrongPasswordException() {
        super(WRONG_PW_ERROR);
    }
}
