package kuit.subway.exception.unauthorized;

import kuit.subway.exception.ExceptionContext;
import kuit.subway.exception.SubwayException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends SubwayException {
    public UnauthorizedException(ExceptionContext context) {
        super(HttpStatus.UNAUTHORIZED, context.getMessage(), context.getCode());
    }
}
