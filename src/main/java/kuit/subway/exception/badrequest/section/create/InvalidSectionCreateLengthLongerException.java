package kuit.subway.exception.badrequest.section.create;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

@Getter
public class InvalidSectionCreateLengthLongerException extends BadRequestException {
    public InvalidSectionCreateLengthLongerException() {
        super("역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크거나 같으면 안된다.", 2008);
    }
}

