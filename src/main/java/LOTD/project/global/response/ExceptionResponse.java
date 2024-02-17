package LOTD.project.global.response;

import LOTD.project.global.exception.ExceptionCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ExceptionResponse {

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    public ExceptionResponse(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getHttpStatus();
        this.errorCode = exceptionCode.getErrorCode();
        this.message = exceptionCode.getMessage();
    }

}