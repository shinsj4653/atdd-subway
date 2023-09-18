package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_SECTION_CREATE_DOWN_STATION_ERROR;

@Getter
public class InvalidSectionCreateDownStationException extends BadRequestException {
    public InvalidSectionCreateDownStationException() {
        super(INVALID_SECTION_CREATE_DOWN_STATION_ERROR);
    }
}
