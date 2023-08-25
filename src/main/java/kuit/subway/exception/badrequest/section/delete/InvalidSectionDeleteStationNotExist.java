package kuit.subway.exception.badrequest.section.delete;

import kuit.subway.exception.ExceptionContext;
import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_SECTION_DELETE_STATION_NOT_EXIST_ERROR;

@Getter
public class InvalidSectionDeleteStationNotExist extends BadRequestException {
    public InvalidSectionDeleteStationNotExist(ExceptionContext context) {
        super(INVALID_SECTION_DELETE_STATION_NOT_EXIST_ERROR);
    }
}
