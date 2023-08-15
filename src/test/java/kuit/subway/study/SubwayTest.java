package kuit.subway.study;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.domain.Station;
import kuit.subway.repository.StationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

public class SubwayTest extends AcceptanceTest {

    @Autowired
    StationRepository stationRepository;

    @Test
    void createSubway() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "강남역");

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/stations")
                .then().log().all()
                .extract();

        Assertions.assertEquals(200, extract.statusCode());
    }

    @Test
    void getAllSubway() {

        Station station1 = new Station("강남역");
        Station station2 = new Station("성수역");

        stationRepository.save(station1);
        stationRepository.save(station2);

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/stations")
                .then().log().all()
                .extract();

        Assertions.assertEquals(2, extract.jsonPath().getList("").size());
    }

    @Test
    void deleteSubway() {

        Station station1 = new Station("강남역");
        stationRepository.save(station1);

        Long id = station1.getId();

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/stations/" + id)
                .then().log().all()
                .extract();

        Assertions.assertEquals(200, extract.statusCode());

    }

}
