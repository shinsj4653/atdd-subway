package kuit.subway.utils.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;

import static kuit.subway.study.common.CommonRestAssured.*;
import static kuit.subway.utils.step.LineStep.LINE_PATH;
import static kuit.subway.utils.fixture.SectionFixture.지하철_구간_삭제_요청;
import static kuit.subway.utils.fixture.SectionFixture.지하철_구간_생성_요청;

public class SectionStep {

    public static final String SECTION_PATH = "/sections";

    public static ExtractableResponse<Response> 지하철_구간_등록(Long lineId, Long upStationId, Long downStationId, int distance) {

        SectionCreateRequest req = 지하철_구간_생성_요청(upStationId, downStationId, distance);
        return post(LINE_PATH + "/" + lineId + SECTION_PATH , req);
    }

    public static ExtractableResponse<Response> 지하철_구간_삭제(Long lineId, Long deleteStationId) {

        SectionDeleteRequest req = 지하철_구간_삭제_요청(deleteStationId);
        return deleteWithParam(LINE_PATH + "/" + lineId + SECTION_PATH, req);
    }
}
