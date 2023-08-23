package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

@Getter
public class InvalidSectionCreateBothNotExistExcpetion extends BadRequestException {
    public InvalidSectionCreateBothNotExistExcpetion() {
        super("상행역과 하행역 둘 중 하나도 포함되어있지 않으면 구간 추가 불가하다.", 2006);
    }
}
