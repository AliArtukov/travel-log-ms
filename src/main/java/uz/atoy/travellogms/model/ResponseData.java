package uz.atoy.travellogms.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseData<T> {

    private T result;

    private final String message;

    private final int code;

    public ResponseData(String message) {
        this.message = message;
        this.code = 200;
    }

    public ResponseData(String errorMessage, int code) {
        this.message = errorMessage;
        this.code = code;
    }

    public ResponseData(T result, String message) {
        this.result = result;
        this.message = message;
        this.code = 200;
    }

}
