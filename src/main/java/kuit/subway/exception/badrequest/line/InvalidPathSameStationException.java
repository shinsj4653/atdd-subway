package kuit.subway.exception.badrequest.line;

import kuit.subway.exception.notfound.NotFoundException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_PATH_SAME_STATION_ERROR;

@Getter
public class InvalidPathSameStationException extends NotFoundException {

    public InvalidPathSameStationException() {
        super(INVALID_PATH_SAME_STATION_ERROR);
    }
}
