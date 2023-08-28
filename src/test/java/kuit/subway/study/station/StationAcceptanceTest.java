package kuit.subway.study.station;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static kuit.subway.utils.fixtures.StationFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("지하철역 인수 테스트")
public class StationAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("지하철 역 등록")
    class CreateStation {

        @Nested
        @DisplayName("역 등록 성공")
        class SuccessCase {
            @Test
            @DisplayName("강남역을 등록하고 201 OK를 반환한다.")
            void createStationSuccess() {

                // given
                // when
                ExtractableResponse<Response> 지하철_역_등록_결과 = 지하철_역_등록("강남역");

                // then
                assertEquals(201, 지하철_역_등록_결과.statusCode());
            }
        }

        @Nested
        @DisplayName("역 등록 실패")
        class FailedCase {
            @Test
            @DisplayName("역 이름을 20자 넘어서 등록할 경우, 500 Internal Server Error를 반환한다.")
            void createStationFail() {

                // given
                // when
                ExtractableResponse<Response> 지하철_역_등록_결과 = 지하철_역_등록("강남역역역역역역역역역역역역역역역역역역역역역역역역역역역역역역");

                // then
                assertEquals(500, 지하철_역_등록_결과.statusCode());
            }
        }

    }

    @Nested
    @DisplayName("지하철 역 전제 조회")
    class GetAllStation {

        @Nested
        @DisplayName("역 전체 조회 성공")
        class SuccessCase {
            @Test
            @DisplayName("등록된 강남역과 성수역을 모두 조회하고 200 OK를 반환한다.")
            void getAllStationSuccess() {

                // given
                지하철_역_등록("강남역");
                지하철_역_등록("성수역");

                // when
                ExtractableResponse<Response> 지하철_역_조회_결과 = 지하철_역_전체_조회();

                // then
                assertAll(
                        () -> {assertEquals(200, 지하철_역_조회_결과.statusCode());},
                        () -> {assertEquals(2, 지하철_역_조회_결과.jsonPath().getList("").size());}
                );
            }
        }

    }

    @Nested
    @DisplayName("지하철 역 삭제")
    class DeleteStation {

        @Nested
        @DisplayName("역 삭제 성공")
        class SuccessCase {
            @Test
            @DisplayName("강남역을 등록하고 201 OK를 반환한다.")
            void deleteStationSuccess() {

                // given
                Long id = 지하철_역_등록("강남역").jsonPath().getLong("id");

                // when
                ExtractableResponse<Response> 지하철_역_삭제_결과 = 지하철_역_삭제(id);

                // then
                assertEquals(200, 지하철_역_삭제_결과.statusCode());
            }
        }

        @Nested
        @DisplayName("역 삭제 실패")
        class FailedCase {
            @Test
            @DisplayName("존재하지 않는 역을 삭제하려고 할시, 404 Not Found Error가 뜬다.")
            void deleteStationFail() {

                // given
                Long id = 지하철_역_등록("강남역").jsonPath().getLong("id");

                // when
                ExtractableResponse<Response> 지하철_역_삭제_결과 = 지하철_역_삭제(id + 1);

                // then
                assertEquals(404, 지하철_역_삭제_결과.statusCode());
            }
        }

    }

}
