package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static kuit.subway.study.common.CommonRestAssured.post;

@DisplayName("지하철 노선 인수 테스트")
public class LineAcceptanceTest extends AcceptanceTest {

    public static final String STATION_PATH = "/stations";
    public static final String LINE_PATH = "/lines";

    public static ExtractableResponse<Response> 더미_데이터_생성_요청(String url, Map params) {
        return post(url, params);
    }
}
