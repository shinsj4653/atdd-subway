package kuit.subway.exception.badrequest.line;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_PATH_STATIONS_NOT_CONNECTED_ERROR;

@Getter
public class InvalidPathNotConnectedException extends BadRequestException {
    public InvalidPathNotConnectedException() {
        super(INVALID_PATH_STATIONS_NOT_CONNECTED_ERROR);
    }
}
