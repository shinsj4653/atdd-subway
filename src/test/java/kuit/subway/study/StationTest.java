package kuit.subway.study;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.CreateStationRequest;
import kuit.subway.repository.StationRepository;
import org.apache.http.HttpStatus;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class StationTest extends AcceptanceTest {

    private static final String STATION_PATH = "/stations";

    public static ExtractableResponse<Response> 지하철_노선_생성_요청(CreateStationRequest req) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().post(STATION_PATH)
                .then().log().all()
                .extract();
    }



    @DisplayName("지하철역 생성 인수 테스트")
    @Test
    void createStation() {

        // given, when
        ExtractableResponse<Response> res = 지하철_노선_생성_요청(new CreateStationRequest("강남역"));

        // then
        assertEquals(200, res.statusCode());
    }

    @DisplayName("지하철역 목록 조회 인수 테스트")

    @Test
    void getAllStations() {

        지하철_노선_생성_요청(new CreateStationRequest("강남역"));
        지하철_노선_생성_요청(new CreateStationRequest("성수역"));

        ExtractableResponse<Response> res = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(STATION_PATH)
                .then().log().all()
                .extract();

        assertEquals(2, res.jsonPath().getList("").size());
    }

    @DisplayName("지하철역 삭제 인수 테스트")
    @Test
    void deleteStation() {

        ExtractableResponse<Response> res = 지하철_노선_생성_요청(new CreateStationRequest("강남역"));
        Long id = res.jsonPath().getLong("id");

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(STATION_PATH + "/" + id)
                .then().log().all()
                .extract();

        assertEquals(200, extract.statusCode());

    }

}
