package kuit.subway.exception.badrequest.section.delete;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_SECTION_DELETE_LAST_STATION_ERROR;

@Getter
public class InvalidSectionDeleteLastStationException extends BadRequestException {
    public InvalidSectionDeleteLastStationException() {
        super(INVALID_SECTION_DELETE_LAST_STATION_ERROR);
    }
}
