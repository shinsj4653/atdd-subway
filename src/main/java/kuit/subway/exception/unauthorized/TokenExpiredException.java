package kuit.subway.exception.unauthorized;

import kuit.subway.exception.ExceptionContext;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.TOKEN_EXPIRED_ERROR;

@Getter
public class TokenExpiredException extends UnauthorizedException{
    public TokenExpiredException() {
        super(TOKEN_EXPIRED_ERROR);
    }
}
