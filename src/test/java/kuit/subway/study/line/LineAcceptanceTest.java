package kuit.subway.study.line;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;

import kuit.subway.utils.fixture.SectionFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kuit.subway.utils.step.LineStep.*;
import static kuit.subway.utils.step.SectionStep.지하철_구간_등록;
import static kuit.subway.utils.step.StationStep.지하철_역_등록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("지하철 노선 인수 테스트")
public class LineAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("지하철 노선 생성 후 201 OK를 반환한다.")
    void createLine() {

        // given
        지하철_역_등록("강남역");
        지하철_역_등록("성수역");

        // when
        ExtractableResponse<Response> 지하철_노선_등록_결과 = 지하철_노선_등록("와우선", "green", 10, 1L, 2L, 5);

        // then
        assertEquals(201, 지하철_노선_등록_결과.statusCode());
    }

    @Test
    @DisplayName("등록한 지하철 노선을 조회하면 200 OK를 반환한다. 등록되어 있지 않으면, 400 Bad Request를 반환한다.")
    void getLineById() {

        // given
        Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
        Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
        Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id, station2Id, 5).jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_조회_실패_결과 = 지하철_노선_식별자로_조회(lineId + 1);
        ExtractableResponse<Response> 지하철_노선_조회_성공_결과 = 지하철_노선_식별자로_조회(lineId);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> {
                    assertEquals(404, 지하철_노선_조회_실패_결과.statusCode());
                },
                () -> {
                    assertEquals(200, 지하철_노선_조회_성공_결과.statusCode());
                }
        );
    }

    @Test
    @DisplayName("등록된 모든 지하철 노선을 조회하고 200 OK를 반환한다.")
    void getAllLines() {

        // given
        Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
        Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
        ;
        지하철_노선_등록("와우선", "green", 10, station1Id, station2Id, 5);
        지하철_노선_등록("싱준선", "green", 20, station2Id, station1Id, 10);

        // when
        ExtractableResponse<Response> 지하철_노선_전체목록_조회_결과 = 지하철_노선_전체목록_조회();

        // then
        assertAll(
                () -> {
                    assertEquals(2, 지하철_노선_전체목록_조회_결과.jsonPath().getList("").size());
                },
                () -> {
                    assertEquals(200, 지하철_노선_전체목록_조회_결과.statusCode());
                }
        );
    }

    @Test
    @DisplayName("등록된 지하철 노선을 수정하고 200 OK를 반환하다. 등록되어 있지 않으면 400 Bad Request를 반환한다.")
    void updateLine() {

        // given
        Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
        Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
        ;
        Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id, station2Id, 5).jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_수정_실패_결과 = 지하철_노선_식별자로_조회(lineId + 1);
        ExtractableResponse<Response> 지하철_노선_수정_성공_결과 = 지하철_노선_수정(lineId, "신분당선", "red", 15);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> {
                    assertEquals(404, 지하철_노선_수정_실패_결과.statusCode());
                },
                () -> {
                    assertEquals(200, 지하철_노선_수정_성공_결과.statusCode());
                }
        );
    }

    @Test
    @DisplayName("등록된 지하철 노선을 삭제 후 200 OK를 반환한다. 등록되어 있지 않으면 400 Bad Request를 반환한다.")
    void deleteLine() {

        // given
        Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
        Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");;
        Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id, station2Id, 5).jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 지하철_노선_삭제_실패_결과 = 지하철_노선_삭제(lineId + 1);
        ExtractableResponse<Response> 지하철_노선_삭제_성공_결과 = 지하철_노선_삭제(lineId);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> {
                    assertEquals(404, 지하철_노선_삭제_실패_결과.statusCode());
                },
                () -> {
                    assertEquals(200, 지하철_노선_삭제_성공_결과.statusCode());
                }
        );
    }

    @Test
    @DisplayName("주어진 출발역과 도착역이 연결된 경로 중, 가장 최단 경로를 조회하고 200 OK를 반환한다.")
    void getPath() {
        // given
        Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
        Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
        Long station3Id = 지하철_역_등록("구디역").jsonPath().getLong("id");
        Long lineId1 = 지하철_노선_등록("와우선", "green", 10, station1Id, station2Id, 1).jsonPath().getLong("id");
        지하철_노선_등록("호우선", "blue", 20, station1Id, station3Id, 10).jsonPath().getLong("id");
        지하철_구간_등록(lineId1, station2Id, station3Id, 1);

        // when
        ExtractableResponse<Response> 지하철_노선_경로_조회_결과 = 지하철_노선_경로_조회(station1Id, station3Id);

        // then
        assertThat(지하철_노선_경로_조회_결과.jsonPath().getList("stations")).hasSize(3)
                .extracting("name")
                .containsExactly("강남역", "성수역", "구디역");
    }
}
