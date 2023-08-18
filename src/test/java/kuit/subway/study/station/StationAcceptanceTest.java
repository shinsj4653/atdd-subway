package kuit.subway.study.station;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.request.station.CreateStationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kuit.subway.study.common.CommonRestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
@DisplayName("지하철역 인수 테스트")
public class StationAcceptanceTest extends AcceptanceTest {


    public static final String STATION_PATH = "/stations";

    @DisplayName("강남역을 등록하고 201 OK를 반환한다.")
    @Test
    void createStation() {

        // given
        CreateStationRequest req = new CreateStationRequest("강남역");

        // when
        ExtractableResponse<Response> res = post(STATION_PATH, req);

        // then
        assertEquals(201, res.statusCode());
    }

    @DisplayName("등록된 강남역과 성수역을 모두 조회하고 200 OK를 반환한다.")
    @Test
    void getAllStations() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        post(STATION_PATH, station1);
        post(STATION_PATH, station2);

        // when
        ExtractableResponse<Response> res = get(STATION_PATH);

        // then
        assertEquals(200, res.statusCode());
        assertEquals(2, res.jsonPath().getList("").size());
    }

    @DisplayName("등록된 강남역을 삭제하고 200 OK를 반환한다.")
    @Test
    void deleteStation() {

        // given
        CreateStationRequest station = new CreateStationRequest("강남역");
        ExtractableResponse<Response> res = post(STATION_PATH, station);
        Long id = res.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> deleteResponse = delete(STATION_PATH + "/" + id);

        // then
        assertEquals(200, deleteResponse.statusCode());

    }

}
