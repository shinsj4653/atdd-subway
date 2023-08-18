package kuit.subway.study.station;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.AcceptanceTest;
import kuit.subway.dto.request.station.CreateStationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kuit.subway.study.common.CommonRestAssured.*;
import static kuit.subway.utils.fixtures.StationFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
@DisplayName("지하철역 인수 테스트")
public class StationAcceptanceTest extends AcceptanceTest {
    @DisplayName("강남역을 등록하고 201 OK를 반환한다.")
    @Test
    void createStation() {

        // given
        // when
        ExtractableResponse<Response> 역_등록_결과 = 역_등록("강남역");

        // then
        assertEquals(201, 역_등록_결과.statusCode());
    }

    @DisplayName("등록된 강남역과 성수역을 모두 조회하고 200 OK를 반환한다.")
    @Test
    void getAllStations() {

        // given
        역_등록("강남역");
        역_등록("성수역");

        // when
        ExtractableResponse<Response> 역_조회_결과 = 역_조회();

        // then
        assertEquals(200, 역_조회_결과.statusCode());
        assertEquals(2, 역_조회_결과.jsonPath().getList("").size());
    }

    @DisplayName("등록된 강남역을 삭제하고 200 OK를 반환한다.")
    @Test
    void deleteStation() {

        // given
        ExtractableResponse<Response> res = 역_등록("강남역");
        Long id = res.jsonPath().getLong("id");

        // when
        ExtractableResponse<Response> 역_삭제_결과 = 역_삭제(id);

        // then
        assertEquals(200, 역_삭제_결과.statusCode());

    }

}
