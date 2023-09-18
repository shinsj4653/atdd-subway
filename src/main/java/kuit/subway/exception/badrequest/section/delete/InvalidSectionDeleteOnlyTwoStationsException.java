package kuit.subway.exception.badrequest.section.delete;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

import static kuit.subway.exception.CustomExceptionContext.INVALID_SECTION_DELETE_ONLY_TWO_STATIONS_ERROR;

@Getter
public class InvalidSectionDeleteOnlyTwoStationsException extends BadRequestException {

    public InvalidSectionDeleteOnlyTwoStationsException() {
        super(INVALID_SECTION_DELETE_ONLY_TWO_STATIONS_ERROR);
   }
}
