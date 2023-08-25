package kuit.subway.exception.notfound.station;

import kuit.subway.exception.notfound.NotFoundException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.NOT_FOUND_STATION_ERROR;

@Getter
public class NotFoundStationException extends NotFoundException {
       public NotFoundStationException() {
           super(NOT_FOUND_STATION_ERROR);
       }
}
