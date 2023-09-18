package kuit.subway.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


public interface ExceptionContext {
    String getMessage();
    int getCode();
}
