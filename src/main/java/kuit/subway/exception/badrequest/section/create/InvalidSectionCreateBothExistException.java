package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

@Getter
public class InvalidSectionCreateBothExistException extends BadRequestException {

    public InvalidSectionCreateBothExistException() {
        super("상행역과 하행역이 이미 노선에 모두 등록되어 있다면 구간 추가 불가하다.", 2007);
    }
}
