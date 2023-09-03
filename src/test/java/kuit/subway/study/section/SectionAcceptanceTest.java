package kuit.subway.study.section;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static kuit.subway.utils.step.LineStep.지하철_노선_등록;
import static kuit.subway.utils.step.SectionStep.지하철_구간_등록;
import static kuit.subway.utils.step.StationStep.지하철_역_등록;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("지하철 구간 인수 테스트")
public class SectionAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("지하철 구간 등록 인수 테스트")
    class CreateSection {

        @Nested
        @DisplayName("지하철 구간 등록 성공 케이스")
        class SuccessCase {

            @Test
            @DisplayName("조건에 맞게 지하철 구간 등록할 시, 201 OK를 반환한다.")
            void createSectionSuccess() {
                Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
                Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
                Long station3Id = 지하철_역_등록("논현역").jsonPath().getLong("id");
                Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id,  station2Id, 5).jsonPath().getLong("id");

                // when
                ExtractableResponse<Response> successRes = 지하철_구간_등록(lineId, station2Id, station3Id, 1);

                // then
               assertEquals(201, successRes.statusCode());
            }

        }


        @Nested
        @DisplayName("지하철 구간 등록 실패 케이스")
        class FailedCase {

            @Test
            @DisplayName("같은 두 역을 구간으로 추가하려고 할시, 400 Bad Request를 반환한다.")
            void createSectionFail1() {
                Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
                Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
                Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id,  station2Id, 5).jsonPath().getLong("id");

                // when
                ExtractableResponse<Response> failedRes = 지하철_구간_등록(lineId, station2Id, station2Id, 1);

                // then
                assertEquals(400, failedRes.statusCode());
            }

            @Test
            @DisplayName("상행역과 하행역 둘 중 하나도 포함되어있지 않으면, 400 Bad Request를 반환한다.")
            void createSectionFail2() {
                Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
                Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
                Long station3Id = 지하철_역_등록("논현역").jsonPath().getLong("id");
                Long station4Id = 지하철_역_등록("이수역").jsonPath().getLong("id");
                Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id,  station2Id, 5).jsonPath().getLong("id");

                // when
                ExtractableResponse<Response> failedRes = 지하철_구간_등록(lineId, station3Id, station4Id, 1);

                // then
                assertEquals(400, failedRes.statusCode());
            }

            @Test
            @DisplayName("상행역과 하행역 둘 다 모두 포함되어 있으면, 400 Bad Request를 반환한다.")
            void createSectionFail3() {
                Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
                Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
                Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id,  station2Id, 5).jsonPath().getLong("id");

                // when
                ExtractableResponse<Response> failedRes = 지하철_구간_등록(lineId, station2Id, station1Id, 1);

                // then
                assertEquals(400, failedRes.statusCode());
            }
            @Test
            @DisplayName("상행역과 하행역 둘 다 모두 포함되어 있으면, 400 Bad Request를 반환한다.")
            void createSectionFail4() {
                Long station1Id = 지하철_역_등록("강남역").jsonPath().getLong("id");
                Long station2Id = 지하철_역_등록("성수역").jsonPath().getLong("id");
                Long lineId = 지하철_노선_등록("와우선", "green", 10, station1Id,  station2Id, 5).jsonPath().getLong("id");

                // when
                ExtractableResponse<Response> failedRes = 지하철_구간_등록(lineId, station2Id, station1Id, 1);

                // then
                assertEquals(400, failedRes.statusCode());
            }

        }

    }

}
