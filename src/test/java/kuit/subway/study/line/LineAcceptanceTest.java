package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.request.station.CreateStationRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kuit.subway.study.common.CommonRestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("지하철 노선 인수 테스트")
public class LineAcceptanceTest extends AcceptanceTest {

    public static final String STATION_PATH = "/stations";
    public static final String LINE_PATH = "/lines";

    private ExtractableResponse<Response> 지하철_노선_생성_요청(CreateStationRequest downStation, CreateStationRequest upStation) {


        ExtractableResponse<Response> stationRes1 = post(STATION_PATH, downStation);
        ExtractableResponse<Response> stationRes2 = post(STATION_PATH, upStation);

        Long downStationId = stationRes1.jsonPath().getLong("id");
        Long upStationId = stationRes2.jsonPath().getLong("id");

        CreateLineRequest req = new CreateLineRequest("green", 10, "경춘선", downStationId, upStationId);
        return post(LINE_PATH, req);
    }

    @DisplayName("지하철 노선 생성 후 201 OK를 반환한다.")
    @Test
    void createLine() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");

        // when
        ExtractableResponse<Response> res = 지하철_노선_생성_요청(station1, station2);

        // then
        assertEquals(201, res.statusCode());
    }

    @DisplayName("등록한 지하철 노선을 조회하면 200 OK를 반환한다. 그렇지 않으면, 400 Bad Request를 반환한다.")
    @Test
    void getLineById() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        ExtractableResponse<Response> res = 지하철_노선_생성_요청(station1, station2);

        // when
        ExtractableResponse<Response> failedRes = get(LINE_PATH + "/" + 2);
        ExtractableResponse<Response> successRes = get(LINE_PATH + "/" + res.jsonPath().getLong("id"));

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, failedRes.statusCode()); },
                () -> { assertEquals(200, successRes.statusCode()); }
        );
    }

    @DisplayName("등록된 모든 지하철 노선을 조회하고 200 OK를 반환한다.")
    @Test
    void getAllLines() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        CreateStationRequest station3 = new CreateStationRequest("논현역");
        CreateStationRequest station4 = new CreateStationRequest("이수역");

        지하철_노선_생성_요청(station1, station2);
        지하철_노선_생성_요청(station3, station4);

        // when
        ExtractableResponse<Response> res = get(LINE_PATH);

        // then
        assertEquals(200, res.statusCode());
    }

    @DisplayName("등록된 지하철 노선을 수정하고 200 OK를 반환하다. 등록되어 있지 않으면 400 Bad Request를 반환한다.")
    @Test
    void updateLine() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        ExtractableResponse createdRes = 지하철_노선_생성_요청(station1, station2);
        CreateLineRequest req = new CreateLineRequest("red", 15, "신분당선", 2L, 1L);

        // when
        ExtractableResponse<Response> failedRes = get(LINE_PATH + "/" + 2);
        ExtractableResponse<Response> updateRes = put(LINE_PATH + "/" + createdRes.jsonPath().getLong("id"), req);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, failedRes.statusCode()); },
                () -> { assertEquals(200, updateRes.statusCode()); }
        );
    }

    @DisplayName("등록된 지하철 노선을 삭제 후 200 OK를 반환한다. 등록되어 있지 않으면 400 Bad Request를 반환한다.")
    @Test
    void deleteLine() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        ExtractableResponse createdRes = 지하철_노선_생성_요청(station1, station2);

        // when
        ExtractableResponse<Response> failedRes = delete(LINE_PATH + "/" + 2);
        ExtractableResponse<Response> deleteRes = delete(LINE_PATH + "/" + createdRes.jsonPath().getLong("id"));

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, failedRes.statusCode()); },
                () -> { assertEquals(200, deleteRes.statusCode()); }
        );
    }

}
