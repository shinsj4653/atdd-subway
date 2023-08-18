package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.request.line.CreateLineRequest;
import kuit.subway.dto.request.station.CreateStationRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kuit.subway.study.common.CommonRestAssured.*;
import static kuit.subway.utils.fixtures.LineFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("지하철 노선 인수 테스트")
public class LineAcceptanceTest extends AcceptanceTest {

    @DisplayName("지하철 노선 생성 후 201 OK를 반환한다.")
    @Test
    void createLine() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");

        // when
        ExtractableResponse<Response> 지하철_노선_등록_결과 = 지하철_노선_등록(station1, station2);

        // then
        assertEquals(201, 지하철_노선_등록_결과.statusCode());
    }

    @DisplayName("등록한 지하철 노선을 조회하면 200 OK를 반환한다. 등록되어 있지 않으면, 400 Bad Request를 반환한다.")
    @Test
    void getLineById() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        ExtractableResponse<Response> res = 지하철_노선_등록(station1, station2);
        Long id = res.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_조회_실패_결과 = 지하철_노선_식별자로_조회(id + 1);
        ExtractableResponse<Response> 지하철_노선_조회_성공_결과 = 지하철_노선_식별자로_조회(id);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, 지하철_노선_조회_실패_결과.statusCode()); },
                () -> { assertEquals(200, 지하철_노선_조회_성공_결과.statusCode()); }
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

        지하철_노선_등록(station1, station2);
        지하철_노선_등록(station3, station4);

        // when
        ExtractableResponse<Response> 지하철_노선_전체목록_조회_결과 = 지하철_노선_전체목록_조회();

        // then
        assertEquals(200, 지하철_노선_전체목록_조회_결과.statusCode());
    }

    @DisplayName("등록된 지하철 노선을 수정하고 200 OK를 반환하다. 등록되어 있지 않으면 400 Bad Request를 반환한다.")
    @Test
    void updateLine() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        ExtractableResponse createdRes = 지하철_노선_등록(station1, station2);
        CreateLineRequest 지하철_노선_변경_요청 = new CreateLineRequest("red", 15, "신분당선", 2L, 1L);
        Long id = createdRes.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_수정_실패_결과 = 지하철_노선_식별자로_조회(id + 1);
        ExtractableResponse<Response> 지하철_노선_수정_성공_결과 = 지하철_노선_수정(id, 지하철_노선_변경_요청);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, 지하철_노선_수정_실패_결과.statusCode()); },
                () -> { assertEquals(200, 지하철_노선_수정_성공_결과.statusCode()); }
        );
    }

    @DisplayName("등록된 지하철 노선을 삭제 후 200 OK를 반환한다. 등록되어 있지 않으면 400 Bad Request를 반환한다.")
    @Test
    void deleteLine() {

        // given
        CreateStationRequest station1 = new CreateStationRequest("강남역");
        CreateStationRequest station2 = new CreateStationRequest("성수역");
        ExtractableResponse createdRes = 지하철_노선_등록(station1, station2);
        Long id = createdRes.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_삭제_실패_결과 = 지하철_노선_삭제(id + 1);
        ExtractableResponse<Response> 지하철_노선_삭제_성공_결과 = 지하철_노선_삭제(id);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, 지하철_노선_삭제_실패_결과.statusCode()); },
                () -> { assertEquals(200, 지하철_노선_삭제_성공_결과.statusCode()); }
        );
    }

}
