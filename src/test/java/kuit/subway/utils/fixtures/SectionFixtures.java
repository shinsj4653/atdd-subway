package kuit.subway.utils.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;
import kuit.subway.dto.request.station.StationCreateRequest;

import static kuit.subway.study.common.CommonRestAssured.*;
import static kuit.subway.utils.steps.SectionStep.지하철_구간_삭제_요청;
import static kuit.subway.utils.steps.SectionStep.지하철_구간_생성_요청;

public class SectionFixtures {

    public static final String SECTION_PATH = "/sections";

    public static ExtractableResponse<Response> 지하철_구간_등록(Long lineId, Long upStationId, Long downStationId, int distance) {

        SectionCreateRequest req = 지하철_구간_생성_요청(upStationId, downStationId, distance);
        return post(SECTION_PATH + "/" + lineId, req);
    }

    public static ExtractableResponse<Response> 지하철_구간_삭제(Long lineId, Long deleteStationId) {

        SectionDeleteRequest req = 지하철_구간_삭제_요청(deleteStationId);
        return deleteWithParam(SECTION_PATH + "/" + lineId, req);
    }
}
