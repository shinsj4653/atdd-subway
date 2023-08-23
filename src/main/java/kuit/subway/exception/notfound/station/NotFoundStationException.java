package kuit.subway.exception.notfound.station;

import kuit.subway.exception.notfound.NotFoundException;
import lombok.Getter;

@Getter
public class NotFoundStationException extends NotFoundException {
       public NotFoundStationException() {
           super("존재하지 않는 역입니다.", 1001);
       }
}
