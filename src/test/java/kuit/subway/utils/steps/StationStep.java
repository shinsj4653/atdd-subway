package kuit.subway.utils.steps;

import kuit.subway.dto.request.station.StationCreateRequest;

public class StationStep {

    public static StationCreateRequest 지하철_역_생성_요청(String name) {
        return new StationCreateRequest(name);
    }
}
