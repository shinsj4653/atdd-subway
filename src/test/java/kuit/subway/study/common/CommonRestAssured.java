package kuit.subway.study.common;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.CreateStationRequest;
import org.springframework.http.MediaType;

public class CommonRestAssured {
    public static final String STATION_PATH = "/stations";

    public static ExtractableResponse<Response> 지하철_노선_생성_요청(CreateStationRequest req) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().post(STATION_PATH)
                .then().log().all()
                .extract();
    }
}
