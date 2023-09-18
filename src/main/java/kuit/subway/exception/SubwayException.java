package kuit.subway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SubwayException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String message;
    private final int code;

    public SubwayException(HttpStatus httpStatus, String message, int code) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }

}
