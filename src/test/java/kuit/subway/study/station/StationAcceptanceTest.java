package kuit.subway.study.station;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.request.station.CreateStationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static kuit.subway.study.common.CommonRestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
@DisplayName("지하철역 인수 테스트")
public class StationAcceptanceTest extends AcceptanceTest {


    public static final String STATION_PATH = "/stations";

    public static ExtractableResponse<Response> 더미_데이터_생성_요청(String url, Object params) {
        return post(url, params);
    }


    @DisplayName("지하철역 생성 인수 테스트")
    @Test
    void createStation() {

        // given
        CreateStationRequest req = new CreateStationRequest("강남역");

        // when
        ExtractableResponse<Response> res = 더미_데이터_생성_요청(STATION_PATH, req);

        // then
        assertEquals(201, res.statusCode());
    }

    @DisplayName("지하철역 목록 조회 인수 테스트")
    @Test
    void getAllStations() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        더미_데이터_생성_요청(STATION_PATH, station1);
        더미_데이터_생성_요청(STATION_PATH, station2);

        // when
        ExtractableResponse<Response> res = get(STATION_PATH);

        // then
        assertEquals(2, res.jsonPath().getList("").size());
    }

    @DisplayName("지하철역 삭제 인수 테스트")
    @Test
    void deleteStation() {

        // given
        CreateStationRequest station = new CreateStationRequest("강남역");
        ExtractableResponse<Response> res = 더미_데이터_생성_요청(STATION_PATH, station);
        Long id = res.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> deleteResponse = delete(STATION_PATH + "/" + id);

        // then
        assertEquals(200, deleteResponse.statusCode());

    }

}
