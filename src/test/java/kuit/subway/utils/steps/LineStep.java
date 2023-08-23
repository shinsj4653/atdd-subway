package kuit.subway.utils.steps;

import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.utils.fixtures.StationFixtures;

public class LineStep {

    public static LineCreateRequest 지하철_노선_생성_요청(String name, String color, int distance, Long downStationId, Long upStationdId) {
        return new LineCreateRequest(name, color, distance, downStationId, upStationdId);
    }

    public static LineUpdateRequest 지하철_노선_수정_요청(String name, String color, int distance, Long downStationId, Long upStationdId) {
        return new LineUpdateRequest(name, color, distance, downStationId, upStationdId);
    }
}
