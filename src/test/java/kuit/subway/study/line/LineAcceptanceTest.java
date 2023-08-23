package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.station.StationCreateRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kuit.subway.utils.fixtures.LineFixtures.*;
import static kuit.subway.utils.fixtures.StationFixtures.지하철_역_등록;
import static kuit.subway.utils.steps.LineStep.지하철_노선_수정_요청;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("지하철 노선 인수 테스트")
public class LineAcceptanceTest extends AcceptanceTest {

    @DisplayName("지하철 노선 생성 후 201 OK를 반환한다.")
    @Test
    void createLine() {

        // given
        지하철_역_등록("강남역");
        지하철_역_등록("성수역");

        // when
        ExtractableResponse<Response> 지하철_노선_등록_결과 = 지하철_노선_등록("와우선", "green", 10, 1L,  2L);

        // then
        assertEquals(201, 지하철_노선_등록_결과.statusCode());
    }

    @DisplayName("등록한 지하철 노선을 조회하면 200 OK를 반환한다. 등록되어 있지 않으면, 400 Bad Request를 반환한다.")
    @Test
    void getLineById() {

        // given
        지하철_역_등록("강남역");
        지하철_역_등록("성수역");
        ExtractableResponse<Response> res = 지하철_노선_등록("와우선", "green", 10, 1L,  2L);
        Long id = res.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_조회_실패_결과 = 지하철_노선_식별자로_조회(id + 1);
        ExtractableResponse<Response> 지하철_노선_조회_성공_결과 = 지하철_노선_식별자로_조회(id);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(404, 지하철_노선_조회_실패_결과.statusCode()); },
                () -> { assertEquals(200, 지하철_노선_조회_성공_결과.statusCode()); }
        );
    }

    @DisplayName("등록된 모든 지하철 노선을 조회하고 200 OK를 반환한다.")
    @Test
    void getAllLines() {

        // given
        지하철_역_등록("강남역");
        지하철_역_등록("성수역");

        지하철_노선_등록("와우선", "green", 10, 1L,  2L);
        지하철_노선_등록("싱준선", "blue", 15, 1L,  2L);

        // when
        ExtractableResponse<Response> 지하철_노선_전체목록_조회_결과 = 지하철_노선_전체목록_조회();

        // then
        assertAll(
                () -> { assertEquals(2, 지하철_노선_전체목록_조회_결과.jsonPath().getList("").size()); },
                () -> { assertEquals(200, 지하철_노선_전체목록_조회_결과.statusCode()); }
        );
    }

    @DisplayName("등록된 지하철 노선을 수정하고 200 OK를 반환하다. 등록되어 있지 않으면 400 Bad Request를 반환한다.")
    @Test
    void updateLine() {

        // given
        지하철_역_등록("강남역");
        지하철_역_등록("성수역");
        ExtractableResponse<Response> createdRes = 지하철_노선_등록("와우선", "green", 10, 1L,  2L);
        Long id = createdRes.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_수정_실패_결과 = 지하철_노선_식별자로_조회(id + 1);
        ExtractableResponse<Response> 지하철_노선_수정_성공_결과 = 지하철_노선_수정(1L, "신분당선", "red", 15,  2L, 1L);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(404, 지하철_노선_수정_실패_결과.statusCode()); },
                () -> { assertEquals(200, 지하철_노선_수정_성공_결과.statusCode()); }
        );
    }

    @DisplayName("등록된 지하철 노선을 삭제 후 200 OK를 반환한다. 등록되어 있지 않으면 400 Bad Request를 반환한다.")
    @Test
    void deleteLine() {

        // given
        지하철_역_등록("강남역");
        지하철_역_등록("성수역");
        ExtractableResponse<Response> createdRes = 지하철_노선_등록("와우선", "green", 10, 1L,  2L);
        Long id = createdRes.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_삭제_실패_결과 = 지하철_노선_삭제(id + 1);
        ExtractableResponse<Response> 지하철_노선_삭제_성공_결과 = 지하철_노선_삭제(id);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(404, 지하철_노선_삭제_실패_결과.statusCode()); },
                () -> { assertEquals(200, 지하철_노선_삭제_성공_결과.statusCode()); }
        );
    }

}
