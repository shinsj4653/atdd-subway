package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_SECTION_CREATE_UP_STATION_ERROR;

@Getter
public class InvalidSectionCreateUpStationException extends BadRequestException {
    public InvalidSectionCreateUpStationException(){

        super(INVALID_SECTION_CREATE_UP_STATION_ERROR);
    }
}
