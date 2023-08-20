package kuit.subway.utils.fixtures;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.line.LineCreateRequest;
import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.station.StationCreateRequest;

import static kuit.subway.study.common.CommonRestAssured.post;
import static kuit.subway.utils.fixtures.StationFixtures.STATION_PATH;
import static kuit.subway.utils.fixtures.StationFixtures.지하철_역_조회;

public class SectionFixtures {

    public static final String SECTION_PATH = "/sections";

    public static ExtractableResponse<Response> 지하철_구간_등록(Long lineId, Long upStationId, Long downStationId) {

        SectionCreateRequest req = new SectionCreateRequest(upStationId, downStationId);
        return post(SECTION_PATH + "/" + lineId, req);
    }
}
