package kuit.subway.utils.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.request.station.CreateStationRequest;

import static kuit.subway.study.common.CommonRestAssured.*;
import static kuit.subway.utils.fixtures.StationFixtures.STATION_PATH;

public class LineFixtures {

    public static final String LINE_PATH = "/lines";

    public static ExtractableResponse<Response> 지하철_노선_등록(CreateStationRequest downStation, CreateStationRequest upStation) {

        ExtractableResponse<Response> stationRes1 = post(STATION_PATH, downStation);
        ExtractableResponse<Response> stationRes2 = post(STATION_PATH, upStation);

        Long downStationId = stationRes1.jsonPath().getLong("id");
        Long upStationId = stationRes2.jsonPath().getLong("id");

        CreateLineRequest req = new CreateLineRequest("green", 10, "경춘선", downStationId, upStationId);
        return post(LINE_PATH, req);
    }

    public static ExtractableResponse<Response> 지하철_노선_식별자로_조회(Long id) {
        return get(LINE_PATH + "/" + id);
    }

    public static ExtractableResponse<Response> 지하철_노선_전체목록_조회() {
        return get(LINE_PATH);
    }

    public static ExtractableResponse<Response> 지하철_노선_수정(Long id, CreateLineRequest 지하철_변경_요청) {
        return put(LINE_PATH + "/" + id, 지하철_변경_요청);
    }

    public static ExtractableResponse<Response> 지하철_노선_삭제(Long id) {
        return delete(LINE_PATH + "/" + id);
    }

}
