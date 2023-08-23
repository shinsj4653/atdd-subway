package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

@Getter
public class InvalidSectionCreateDownStationException extends BadRequestException {
    public InvalidSectionCreateDownStationException() {
        super("새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.", 2003);
    }
}
