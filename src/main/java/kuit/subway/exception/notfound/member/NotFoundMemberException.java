package kuit.subway.exception.notfound.member;

import kuit.subway.exception.ExceptionContext;
import kuit.subway.exception.notfound.NotFoundException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.NOT_FOUND_MEMBER_ERROR;

@Getter
public class NotFoundMemberException extends NotFoundException {

    public NotFoundMemberException() {
        super(NOT_FOUND_MEMBER_ERROR);
    }
}
