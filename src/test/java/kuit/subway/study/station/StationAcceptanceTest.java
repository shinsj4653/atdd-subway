package kuit.subway.study.station;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static kuit.subway.study.common.CommonRestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
@DisplayName("지하철역 인수 테스트")
public class StationAcceptanceTest extends AcceptanceTest {


    public static final String STATION_PATH = "/stations";

    public static ExtractableResponse<Response> 더미_데이터_생성_요청(String url, Map params) {
        return post(url, params);
    }


    @DisplayName("지하철역 생성 인수 테스트")
    @Test
    void createStation() {

        // given
        Map<String, String> station = new HashMap<>();
        station.put("name", "강남역");

        // when
        ExtractableResponse<Response> res = 더미_데이터_생성_요청(STATION_PATH, station);

        // then
        assertEquals(201, res.statusCode());
    }

    @DisplayName("지하철역 목록 조회 인수 테스트")

    @Test
    void getAllStations() {

        // given
        Map<String, String> station1 = new HashMap<>();
        station1.put("name", "강남역");

        Map<String, String> station2 = new HashMap<>();
        station2.put("name", "성수역");

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
        Map<String, String> station = new HashMap<>();
        station.put("name", "강남역");

        ExtractableResponse<Response> res = 더미_데이터_생성_요청(STATION_PATH, station);
        Long id = res.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> deleteResponse = delete(STATION_PATH + "/" + id);

        // then
        assertEquals(200, deleteResponse.statusCode());

    }

}
