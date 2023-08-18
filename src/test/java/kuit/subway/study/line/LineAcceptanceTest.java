package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.persistence.EntityNotFoundException;
import kuit.subway.AcceptanceTest;
import kuit.subway.domain.Station;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.request.station.CreateStationRequest;
import kuit.subway.dto.response.line.UpdateLineResponse;
import kuit.subway.study.common.CommonRestAssured;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.put;
import static kuit.subway.study.common.CommonRestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("지하철 노선 아이디 기반 조회 인수 테스트")
    @Test
    void getLineById() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");

        // when
        ExtractableResponse<Response> res = createDummyLine(station1, station2);
        ExtractableResponse<Response> failedRes = get(LINE_PATH + "/" + 2);
        ExtractableResponse<Response> successRes = get(LINE_PATH + "/" + res.jsonPath().getLong("id"));

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, failedRes.statusCode()); },
                () -> { assertEquals(200, successRes.statusCode()); }
        );
    }

    @DisplayName("지하철 노선 전체 목록 조회 인수 테스트")
    @Test
    void getAllLines() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        CreateStationRequest station3 = new CreateStationRequest("논현역");
        CreateStationRequest station4 = new CreateStationRequest("이수역");

        // when
        createDummyLine(station1, station2);
        createDummyLine(station3, station4);
        ExtractableResponse<Response> res = get(LINE_PATH);


        // then
        assertEquals(200, res.statusCode());
    }

    @DisplayName("지하철 노선 수정 인수 테스트")
    @Test
    void updateLine() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        ExtractableResponse createdRes = createDummyLine(station1, station2);

        // when
        ExtractableResponse<Response> failedRes = get(LINE_PATH + "/" + 2);
        CreateLineRequest req = new CreateLineRequest("red", 15, "신분당선", 2L, 1L);
        ExtractableResponse<Response> updateRes = CommonRestAssured.put(LINE_PATH + "/" + createdRes.jsonPath().getLong("id"), req);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, failedRes.statusCode()); },
                () -> { assertEquals(200, updateRes.statusCode()); }
        );
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
