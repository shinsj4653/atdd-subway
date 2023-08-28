package kuit.subway.study.section;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kuit.subway.utils.fixtures.LineFixtures.지하철_노선_등록;
import static kuit.subway.utils.fixtures.SectionFixtures.지하철_구간_등록;
import static kuit.subway.utils.fixtures.SectionFixtures.지하철_구간_삭제;
import static kuit.subway.utils.fixtures.StationFixtures.지하철_역_등록;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("지하철 구간 인수 테스트")
public class SectionAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("지하철 구간 등록 후 201 OK와 변경된 노선의 결과를 반환한다.")
    void createSection() {

        // given
        Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
        Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
        Long station3Id = 지하철_역_등록("논현역").jsonPath().getLong("id");
        Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id,  station2Id).jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> failedRes1 = 지하철_구간_등록(lineId, station3Id, station2Id, 1);
        ExtractableResponse<Response> failedRes2 = 지하철_구간_등록(lineId, station2Id, station2Id, 1);
        ExtractableResponse<Response> successRes = 지하철_구간_등록(lineId, station2Id, station3Id, 1);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, failedRes1.statusCode()); },
                () -> { assertEquals(400, failedRes2.statusCode()); },
                () -> { assertEquals(201, successRes.statusCode()); }
        );

    }

    @Test
    @DisplayName("지하철 구간 삭제 후 200 OK와 변경된 노선의 결과를 반환한다.")
    void deleteSection() {

        // given
        Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
        Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
        Long station3Id = 지하철_역_등록("논현역").jsonPath().getLong("id");

        Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id,  station2Id).jsonPath().getLong("id");

        지하철_구간_등록(lineId, station2Id, station3Id, 1);

        // when
        ExtractableResponse<Response> failedRes1 = 지하철_구간_삭제(lineId, station2Id);
        ExtractableResponse<Response> successRes = 지하철_구간_삭제(lineId, station3Id);
        ExtractableResponse<Response> failedRes2 = 지하철_구간_삭제(lineId, station2Id);

        // then
        assertAll(
                // 존재하지 않는 노선 조회 시 에러
                () -> { assertEquals(400, failedRes1.statusCode()); },
                () -> { assertEquals(400, failedRes2.statusCode()); },
                () -> { assertEquals(200, successRes.statusCode()); }
        );
    }

}
