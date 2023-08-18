package kuit.subway.utils.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.station.CreateStationRequest;
import kuit.subway.dto.response.station.CreateStationResponse;

import static kuit.subway.study.common.CommonRestAssured.*;

public class StationFixtures {

    public static final String STATION_PATH = "/stations";

    public static ExtractableResponse<Response> 지하철_역_등록(String name) {
        CreateStationRequest req = new CreateStationRequest(name);
        return post(STATION_PATH, req);
    }

    public static ExtractableResponse<Response> 지하철_역_조회() {
        return get(STATION_PATH);
    }

    public static ExtractableResponse<Response> 지하철_역_삭제(Long id) {
        return delete(STATION_PATH + "/" + id);
    }
}
