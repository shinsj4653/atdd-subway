package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.persistence.EntityNotFoundException;
import kuit.subway.AcceptanceTest;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.request.station.CreateStationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static kuit.subway.study.common.CommonRestAssured.get;
import static kuit.subway.study.common.CommonRestAssured.post;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");

        // when
        ExtractableResponse<Response> res = createDummyLine(station1, station2);

        // then
        assertEquals(201, res.statusCode());
    }

    @DisplayName("지하철 노선 조회 인수 테스트")
    @Test
    void getLineById() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");

        // when
        ExtractableResponse<Response> res = createDummyLine(station1, station2);
        // then
        ExtractableResponse<Response> failedRes = get(LINE_PATH + "/" + 2);
        assertEquals(400, failedRes.statusCode());

        ExtractableResponse<Response> successRes = get(LINE_PATH + "/" + res.jsonPath().getLong("id"));
        assertEquals(200, successRes.statusCode());
    }
    

    private ExtractableResponse<Response> createDummyLine(CreateStationRequest downStation, CreateStationRequest upStation) {


        ExtractableResponse<Response> stationRes1 = 더미_데이터_생성_요청(STATION_PATH, downStation);
        ExtractableResponse<Response> stationRes2 = 더미_데이터_생성_요청(STATION_PATH, upStation);

        Long downStationId = stationRes1.jsonPath().getLong("id");
        Long upStationId = stationRes2.jsonPath().getLong("id");

        CreateLineRequest req = new CreateLineRequest("green", 10, "경춘선", downStationId, upStationId);
        return 더미_데이터_생성_요청(LINE_PATH, req);
    }

}
