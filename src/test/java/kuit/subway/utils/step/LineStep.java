package kuit.subway.utils.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.line.PathReadRequest;

import static kuit.subway.study.common.CommonRestAssured.*;
import static kuit.subway.utils.fixture.LineFixture.*;

public class LineStep {

    public static final String LINE_PATH = "/lines";

    public static ExtractableResponse<Response> 지하철_노선_등록(String name, String color, int lineDistance, Long upStationId, Long downStationId, int sectionDistance) {

        LineCreateRequest req = 지하철_노선_생성_요청(name, color, lineDistance, upStationId, downStationId, sectionDistance);
        return post(LINE_PATH, req);
    }

    public static ExtractableResponse<Response> 지하철_노선_식별자로_조회(Long id) {
        return get(LINE_PATH + "/" + id);
    }

    public static ExtractableResponse<Response> 지하철_노선_전체목록_조회() {
        return get(LINE_PATH);
    }

    public static ExtractableResponse<Response> 지하철_노선_수정(Long lineId, String name, String color, int lineDistance) {
        LineUpdateRequest req = 지하철_노선_수정_요청(name, color, lineDistance);
        return put(LINE_PATH + "/" + lineId, req);
    }

    public static ExtractableResponse<Response> 지하철_노선_삭제(Long id) {
        return delete(LINE_PATH + "/" + id);
    }
    public static ExtractableResponse<Response> 지하철_노선_경로_조회(Long startStationId, Long endStationId) {
        PathReadRequest req = 지하철_경로_조회_요청(startStationId, endStationId);
        return post(LINE_PATH + "/path", req);
    }


}
