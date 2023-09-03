package kuit.subway.utils.fixture;

import kuit.subway.dto.request.section.SectionCreateRequest;
import kuit.subway.dto.request.section.SectionDeleteRequest;

public class SectionFixture {

    public static SectionCreateRequest 지하철_구간_생성_요청(Long downStationId, Long upStationdId, int distance) {
        return new SectionCreateRequest(downStationId, upStationdId, distance);
    }

    public static SectionDeleteRequest 지하철_구간_삭제_요청(Long deleteStationId) {
        return new SectionDeleteRequest(deleteStationId);
    }
}
