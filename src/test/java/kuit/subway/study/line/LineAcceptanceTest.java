package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.domain.Station;
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
        Station station1 = new Station("강남역");
        Station station2 = new Station("성수역");

        // when
        ExtractableResponse<Response> res = createDummyLine(station1, station2);

        // then
        assertEquals(201, res.statusCode());
    }

    @DisplayName("지하철 노선 조회 인수 테스트")
    @Test
    void getLineById() {

        // given
        Station station1 = new Station("강남역");
        Station station2 = new Station("성수역");

        // when
        ExtractableResponse<Response> res = createDummyLine(station1, station2);

        // then
        assertEquals(201, res.statusCode());
    }

    private ExtractableResponse<Response> createDummyLine(Station downStation, Station upStation) {


        ExtractableResponse<Response> stationRes1 = 더미_데이터_생성_요청(STATION_PATH, downStation);
        ExtractableResponse<Response> stationRes2 = 더미_데이터_생성_요청(STATION_PATH, upStation);

        Long downStationId = stationRes1.jsonPath().getLong("id");
        Long upStationId = stationRes2.jsonPath().getLong("id");

        CreateLineRequest req = new CreateLineRequest("green", 10, "경춘선", downStationId, upStationId);
        return 더미_데이터_생성_요청(LINE_PATH, req);
    }

}
