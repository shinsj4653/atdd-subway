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
    @Autowired
    StationRepository stationRepository;

    @DisplayName("지하철역 생성 인수 테스트")
    @Test
    void createStation() {

        // given
        CreateStationRequest req = new CreateStationRequest("강남역");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(req)
                .when().post(STATION_PATH)
                .then().log().all()
                .extract();

        // then
        assertEquals(200, response.statusCode());
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

        assertEquals(2, extract.jsonPath().getList("").size());
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

        assertEquals(200, extract.statusCode());

    }

}
