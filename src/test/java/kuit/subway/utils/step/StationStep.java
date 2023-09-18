package kuit.subway.utils.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.station.StationCreateRequest;

import static kuit.subway.study.common.CommonRestAssured.*;
import static kuit.subway.utils.fixture.StationFixture.지하철_역_생성_요청;

public class StationStep {

    public static final String STATION_PATH = "/stations";

    public static ExtractableResponse<Response> 지하철_역_등록(String name) {
        StationCreateRequest req = 지하철_역_생성_요청(name);
        return post(STATION_PATH, req);
    }

    public static ExtractableResponse<Response> 지하철_역_전체_조회() {
        return get(STATION_PATH);
    }

    public static ExtractableResponse<Response> 지하철_역_식별자로_조회(Long id) { return get(STATION_PATH + "/" + id); }

    public static ExtractableResponse<Response> 지하철_역_삭제(Long id) {
        return delete(STATION_PATH + "/" + id);
    }
}
