package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
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

    public static ExtractableResponse<Response> 더미_데이터_생성_요청(String url, Map params) {
        return post(url, params);
    }

    @DisplayName("지하철 노선 생성 인수 테스트")
    @Test
    void createLine() {

        // given
        Map<String, String> station = new HashMap<>();
        station.put("name", "강남역");

        // when
        ExtractableResponse<Response> res = 더미_데이터_생성_요청(STATION_PATH, station);

        // then
        assertEquals(201, res.statusCode());
    }


}
