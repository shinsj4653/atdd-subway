package kuit.subway.exception.notfound.line;

import kuit.subway.exception.notfound.NotFoundException;
import lombok.Getter;

@Getter
public class NotFoundLineException extends NotFoundException {
    public NotFoundLineException() {
        super("존재하지 않는 노선입니다.", 1002);
    }
}
