package kuit.subway.exception.badrequest.station;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_LINE_STATION_ERROR;

@Getter
public class InvalidLineStationException extends BadRequestException {
    public InvalidLineStationException(){
        super(INVALID_LINE_STATION_ERROR);
    }
}
