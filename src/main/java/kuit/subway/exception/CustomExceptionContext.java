package kuit.subway.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionContext implements ExceptionContext {

    // BAD_REQUEST
    BLANK_TOKEN_ERROR("토큰이 비어있습니다.", 1010),

    INVALID_PATH_SAME_STATION_ERROR("출발역과 도착역이 같으면 안됩니다", 1010),
    INVALID_PATH_STATIONS_NOT_CONNECTED_ERROR("출발역과 도착역이 연결되어 있지 않습니다.", 1011),

    INVALID_LINE_STATION_ERROR("상행역과 하행역이 같으면 안됩니다.", 1001),

    INVALID_SECTION_CREATE_BOTH_EXIST("상행역과 하행역이 이미 노선에 모두 등록되어 있다면 구간 추가 불가하다.", 1002),
    INVALID_SECTION_CREATE_BOTH_NOT_EXIST("상행역과 하행역 둘 중 하나도 포함되어있지 않으면 구간 추가 불가하다.", 1003),
    INVALID_SECTION_CREATE_DOWN_STATION_ERROR("새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.", 1004),
    INVALID_SECTION_CREATE_LENGTH_LONGER_ERROR("역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크거나 같으면 안된다.", 1005),
    INVALID_SECTION_CREATE_UP_STATION_ERROR("새로운 구간의 상행역은 해당 노선에 등록되어있는 하행 종점역이어야 한다.", 1006),

    INVALID_SECTION_DELETE_LAST_STATION_ERROR("지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다.", 1007),
    INVALID_SECTION_DELETE_ONLY_TWO_STATIONS_ERROR("지하철 노선에 상행 종점역과 하행 종점역만 있는 경우(구간이 1개인 경우) 역을 삭제할 수 없다.", 1008),
    INVALID_SECTION_DELETE_STATION_NOT_EXIST_ERROR("지하철 노선에 등록되어 있지 않은 역은 제거 불가", 1009),

    // NOT_FOUND
    NOT_FOUND_LINE_ERROR("존재하지 않는 노선입니다.", 2001),
    NOT_FOUND_SECTION_HAVING_CYCLE_ERROR("해당 노선에 사이클이 존재합니다.", 2002),
    NOT_FOUND_STATION_ERROR("존재하지 않는 역입니다.", 2003),
    NOT_FOUND_MEMBER_ERROR("존재하지 않는 회원입니다.", 2004),

    // UNAUTHORIZED
    INVALID_BEARER_ERROR("로그인이 필요한 서비스입니다.", 2010),
    INVALID_TOKEN_ERROR("유효하지 않은 Token 입니다. 다시 로그인 해주세요.", 2011),
    TOKEN_EXPIRED_ERROR("로그인 인증 유효기간이 만료되었습니다. 다시 로그인 해주세요.", 2012),
    INVALID_PW_ERROR("로그인에 실패하셨습니다. 비밀번호를 다시 확인해주세요.", 2020);



    private final String message;
    private final int code;

}
