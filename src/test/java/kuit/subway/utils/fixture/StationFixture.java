package kuit.subway.utils.fixture;

import kuit.subway.dto.request.station.StationCreateRequest;

public class StationFixture {

    public static StationCreateRequest 지하철_역_생성_요청(String name) {
        return new StationCreateRequest(name);
    }
}
