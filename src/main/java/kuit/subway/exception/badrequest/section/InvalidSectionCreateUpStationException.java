package kuit.subway.exception.badrequest.section;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

@Getter
public class InvalidSectionCreateUpStationException extends BadRequestException {
    public InvalidSectionCreateUpStationException(){

        super("새로운 구간의 상행역은 해당 노선에 등록되어있는 하행 종점역이어야 한다.", 2002);
    }
}
