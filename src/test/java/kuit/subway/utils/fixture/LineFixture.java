package kuit.subway.utils.fixture;

import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.line.PathReadRequest;

public class LineFixture {

    public static LineCreateRequest 지하철_노선_생성_요청(String name, String color, int lineDistance, Long upStationId, Long downStationId, int sectionDistance) {
        return new LineCreateRequest(name, color, lineDistance, upStationId, downStationId, sectionDistance);
    }

    public static LineUpdateRequest 지하철_노선_수정_요청(String name, String color, int lineDistance) {
        return new LineUpdateRequest(name, color, lineDistance);
    }

    public static PathReadRequest 지하철_경로_조회_요청(Long startStationId, Long endStationId) {
        return new PathReadRequest(startStationId, endStationId);
    }
}
