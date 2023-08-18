package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.request.station.CreateStationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static kuit.subway.study.common.CommonRestAssured.post;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("지하철 노선 인수 테스트")
public class LineAcceptanceTest extends AcceptanceTest {

    public static final String STATION_PATH = "/stations";
    public static final String LINE_PATH = "/lines";

    public static ExtractableResponse<Response> 더미_데이터_생성_요청(String url, Object req) {
        return post(url, req);
    }

    @DisplayName("지하철 노선 생성 인수 테스트")
    @Test
    void createLine() {

        // given
        Map<String, String> station1 = new HashMap<>();
        station1.put("name", "강남역");

        Map<String, String> station2 = new HashMap<>();
        station2.put("name", "성수역");

        ExtractableResponse<Response> stationRes1 = 더미_데이터_생성_요청(STATION_PATH, station1);
        ExtractableResponse<Response> stationRes2 = 더미_데이터_생성_요청(STATION_PATH, station2);

        Long downStationId = stationRes1.jsonPath().getLong("id");
        Long upStationId = stationRes2.jsonPath().getLong("id");

        CreateLineRequest req = new CreateLineRequest("green", 10, "경춘선", downStationId, upStationId);

        // when
        ExtractableResponse<Response> res = 더미_데이터_생성_요청(LINE_PATH, req);

        // then
        assertEquals(201, res.statusCode());
    }


}
