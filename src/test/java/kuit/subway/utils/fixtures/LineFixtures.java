package kuit.subway.utils.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.line.LineUpdateRequest;
import kuit.subway.dto.request.station.StationCreateRequest;

import static kuit.subway.study.common.CommonRestAssured.*;
import static kuit.subway.utils.fixtures.StationFixtures.STATION_PATH;
import static kuit.subway.utils.fixtures.StationFixtures.지하철_역_등록;
import static kuit.subway.utils.steps.LineStep.지하철_노선_생성_요청;
import static kuit.subway.utils.steps.LineStep.지하철_노선_수정_요청;

public class LineFixtures {

    public static final String LINE_PATH = "/lines";

    public static ExtractableResponse<Response> 지하철_노선_등록(String name, String color, int distance) {

        LineCreateRequest req = 지하철_노선_생성_요청(name, color, distance);
        return post(LINE_PATH, req);
    }

    public static ExtractableResponse<Response> 지하철_노선_식별자로_조회(Long id) {
        return get(LINE_PATH + "/" + id);
    }

    public static ExtractableResponse<Response> 지하철_노선_전체목록_조회() {
        return get(LINE_PATH);
    }

    public static ExtractableResponse<Response> 지하철_노선_수정(Long id, String name, String color, int distance, Long upStationId, Long downStationId ) {
        LineUpdateRequest req = 지하철_노선_수정_요청(name, color, distance, downStationId, upStationId);
        return put(LINE_PATH + "/" + id, req);
    }

    public static ExtractableResponse<Response> 지하철_노선_삭제(Long id) {
        return delete(LINE_PATH + "/" + id);
    }

}
