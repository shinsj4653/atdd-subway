package kuit.subway.exception.badrequest;

import lombok.Getter;

@Getter
public class InvalidSectionDeleteLastStationException extends BadRequestException {
    public InvalidSectionDeleteLastStationException() {
        super("지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다.", 2004);
    }
}
