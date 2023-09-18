package kuit.subway.exception.badrequest.member;

import kuit.subway.exception.ExceptionContext;
import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.DUPLICATE_EMAIL_ERROR;

@Getter
public class DuplicateEmailException extends BadRequestException {
    public DuplicateEmailException() {
        super(DUPLICATE_EMAIL_ERROR);
    }
}
