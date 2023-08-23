package kuit.subway.exception.badrequest.section.delete;

import kuit.subway.exception.badrequest.BadRequestException;
import lombok.Getter;

@Getter
public class InvalidSectionDeleteOnlyTwoStationsException extends BadRequestException {

    public InvalidSectionDeleteOnlyTwoStationsException() {
        super("지하철 노선에 상행 종점역과 하행 종점역만 있는 경우(구간이 1개인 경우) 역을 삭제할 수 없다.", 2005);
   }
}
