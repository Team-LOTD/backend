package LOTD.project.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    /**
     * 인증,인가
     */
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_01", "ACCESS TOKEN 또는 REFRESH TOKEN이 만료되었습니다. 로그인을 진행해주세요."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_02", "해당 TOKEN은 유효한 TOKEN이 아닙니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_03", "지원하지 않는 TOKEN입니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "TOKEN_04", "해당 TOKEN은 ACCESS TOKEN이 아닙니다."),
    BLACKLISTED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"TOKEN_05","로그아웃하셨습니다. 로그인이 필요합니다."),
    ALL_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"TOKEN_06","ACCESS TOKEN, REFRESH TOKEN 모두 만료되거나 유효하지 않습니다. 로그인이 필요합니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "TOKEN_07", "인증 타입이 Bearer 타입이 아닙니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_02", "비밀번호가 틀렸습니다."),
    NOT_SAME_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_03", "입력 비밀번호와 확인 비밀번호가 일치하지 않습니다."),
    NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER_04", "존재하지 않는 회원입니다."),
    EXIST_MEMBER_ID(HttpStatus.BAD_REQUEST, "MEMBER_05", "이미 존재하는 아이디입니다."),


    /**
     * 데이터
     */

    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, "1001","데이터를 찾을 수 없습니다."),
    DUPLICATE_KEY(HttpStatus.BAD_REQUEST, "1002","중복된 키 값이 존재합니다."),
    WRONG_REQUEST_DATA(HttpStatus.BAD_REQUEST, "1003", "요청 데이터가 올바르지 않습니다."),
    DATA_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "1004", "중복된 데이터가 이미 존재합니다.");
    ExceptionCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
