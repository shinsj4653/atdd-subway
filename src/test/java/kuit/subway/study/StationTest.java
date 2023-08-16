package kuit.subway.study;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.domain.Station;
import kuit.subway.repository.StationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class StationTest extends AcceptanceTest {

    private static final String STATION_PATH = "/stations";
    @Autowired
    StationRepository stationRepository;

    @DisplayName("지하철역 생성 인수 테스트")
    @Test
    void createStation() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "강남역");

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(STATION_PATH)
                .then().log().all()
                .extract();

        Assertions.assertEquals(200, extract.statusCode());
    }

    @DisplayName("지하철역 목록 조회 인수 테스트")

    @Test
    void getAllStations() {

        Station station1 = new Station("강남역");
        Station station2 = new Station("성수역");

        stationRepository.save(station1);
        stationRepository.save(station2);

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get(STATION_PATH)
                .then().log().all()
                .extract();

        Assertions.assertEquals(2, extract.jsonPath().getList("").size());
    }

    @DisplayName("지하철역 삭제 인수 테스트")
    @Test
    void deleteStation() {

        Station station1 = new Station("강남역");
        stationRepository.save(station1);

        Long id = station1.getId();

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete(STATION_PATH + id)
                .then().log().all()
                .extract();

        Assertions.assertEquals(200, extract.statusCode());

    }

}
