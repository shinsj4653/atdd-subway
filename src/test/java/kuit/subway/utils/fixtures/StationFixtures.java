package kuit.subway.utils.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.station.CreateStationRequest;
import kuit.subway.dto.response.station.CreateStationResponse;

import static kuit.subway.study.common.CommonRestAssured.*;

public class StationFixtures {

    public static final String STATION_PATH = "/stations";

    public static ExtractableResponse<Response> 역_등록(String 역_이름) {
        CreateStationRequest req = new CreateStationRequest(역_이름);
        return post(STATION_PATH, req);
    }

    public static ExtractableResponse<Response> 역_조회() {
        return get(STATION_PATH);
    }

    public static ExtractableResponse<Response> 역_삭제(Long 역_식별자) {
        return delete(STATION_PATH + "/" + 역_식별자);
    }
}
