//package kuit.subway.study.section;
//
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import kuit.subway.AcceptanceTest;
//import kuit.subway.dto.request.station.StationCreateRequest;
//import kuit.subway.dto.response.station.StationDto;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static kuit.subway.utils.fixtures.LineFixtures.지하철_노선_등록;
//import static kuit.subway.utils.fixtures.LineFixtures.지하철_노선_식별자로_조회;
//import static kuit.subway.utils.fixtures.SectionFixtures.지하철_구간_등록;
//import static kuit.subway.utils.fixtures.SectionFixtures.지하철_구간_삭제;
//import static kuit.subway.utils.fixtures.StationFixtures.지하철_역_등록;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DisplayName("지하철 구간 인수 테스트")
//public class SectionAcceptanceTest extends AcceptanceTest {
//
//    @DisplayName("지하철 구간 등록 후 201 OK와 변경된 노선의 결과를 반환한다.")
//    @Test
//    void createSection() {
//
//        // given
//        StationCreateRequest station1 = new StationCreateRequest("강남역");
//        StationCreateRequest station2 = new StationCreateRequest("성수역");
//        StationCreateRequest station3 = new StationCreateRequest("논현역");
//
//
//        ExtractableResponse<Response> lineRes =  지하철_노선_등록(station1, station2);
//        ExtractableResponse<Response> stationRes = 지하철_역_등록(station3.getName());
//
//        Long lineId = lineRes.jsonPath().getLong("id");
//
//        ExtractableResponse<Response> getLineRes = 지하철_노선_식별자로_조회(lineId);
//
//        // TODO 추후 수정 예정
//        // List<StationDto> result = getLineRes.jsonPath().getList("stations");
//        // Long station2Id = result.get(result.size() - 1).getId();
//        Long station2Id = 2L;
//        Long station3Id = stationRes.jsonPath().getLong("id");
//
//        // when
//        ExtractableResponse<Response> failedRes1 = 지하철_구간_등록(lineId, station3Id, station2Id);
//        ExtractableResponse<Response> failedRes2 = 지하철_구간_등록(lineId, station2Id, station2Id);
//        ExtractableResponse<Response> successRes = 지하철_구간_등록(lineId, station2Id, station3Id);
//
//        // then
//        assertAll(
//                // 존재하지 않는 노선 조회 시 에러
//                () -> { assertEquals(400, failedRes1.statusCode()); },
//                () -> { assertEquals(400, failedRes2.statusCode()); },
//                () -> { assertEquals(201, successRes.statusCode()); }
//        );
//
//    }
//
//    @DisplayName("지하철 구간 삭제 후 200 OK와 변경된 노선의 결과를 반환한다.")
//    @Test
//    void deleteSection() {
//
//        // given
//        StationCreateRequest station1 = new StationCreateRequest("강남역");
//        StationCreateRequest station2 = new StationCreateRequest("성수역");
//        StationCreateRequest station3 = new StationCreateRequest("논현역");
//
//
//        ExtractableResponse<Response> lineRes =  지하철_노선_등록(station1, station2);
//        ExtractableResponse<Response> stationRes = 지하철_역_등록(station3.getName());
//
//        Long lineId = lineRes.jsonPath().getLong("id");
//
//        ExtractableResponse<Response> getLineRes = 지하철_노선_식별자로_조회(lineId);
//
//        // TODO 추후 수정 예정
//        // List<StationDto> result = getLineRes.jsonPath().getList("stations");
//        // Long station2Id = result.get(result.size() - 1).getId();
//        Long station2Id = 2L;
//
//        Long station3Id = stationRes.jsonPath().getLong("id");
//        지하철_구간_등록(lineId, station2Id, station3Id);
//
//        // when
//        ExtractableResponse<Response> failedRes1 = 지하철_구간_삭제(lineId, station2Id);
//        ExtractableResponse<Response> successRes = 지하철_구간_삭제(lineId, station3Id);
//        ExtractableResponse<Response> failedRes2 = 지하철_구간_삭제(lineId, station2Id);
//
//        // then
//        assertAll(
//                // 존재하지 않는 노선 조회 시 에러
//                () -> { assertEquals(400, failedRes1.statusCode()); },
//                () -> { assertEquals(400, failedRes2.statusCode()); },
//                () -> { assertEquals(200, successRes.statusCode()); }
//        );
//    }
//
//}
