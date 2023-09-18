package kuit.subway.exception.badrequest.line;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_PATH_SAME_STATION_ERROR;

@Getter
public class InvalidPathSameStationException extends BadRequestException {

    public InvalidPathSameStationException() {
        super(INVALID_PATH_SAME_STATION_ERROR);
    }
}
