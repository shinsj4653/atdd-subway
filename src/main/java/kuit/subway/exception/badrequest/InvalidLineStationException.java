package kuit.subway.exception.badrequest;

import lombok.Getter;

@Getter
public class InvalidLineStationException extends BadRequestException{
    public InvalidLineStationException(){
        super("상행역과 하행액이 같으면 안됩니다.", 2001);
    }
}
