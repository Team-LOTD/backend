package LOTD.project.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    /**
     * 인증,인가
     */
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_01", "ACCESS TOKEN 또는 REFRESH TOKEN이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_02", "해당 TOKEN은 유효한 TOKEN이 아닙니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_03", "지원하지 않는 TOKEN입니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "TOKEN_04", "해당 TOKEN은 ACCESS TOKEN이 아닙니다."),
    BLACKLISTED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"TOKEN_05","로그아웃하셨습니다. 로그인이 필요합니다."),
    NOT_LOGIN(HttpStatus.UNAUTHORIZED, "TOKEN_06", "TOKEN이 만료되어 로그인이 필요합니다."),
    NOT_EXISTS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_01", "TOKEN이 필요합니다. 로그인을 통해 TOKEN을 발급 받거나, 만료되지 않은 TOKEN을 Authrization Header에 넣어주세요."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "AUTH_02", "인증 타입이 Bearer 타입이 아닙니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_02", "비밀번호가 틀렸습니다."),
    NOT_SAME_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_03", "입력 비밀번호와 확인 비밀번호가 일치하지 않습니다."),
    NOT_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "MEMBER_04", "존재하지 않는 회원입니다."),
    EXIST_MEMBER_ID(HttpStatus.BAD_REQUEST, "MEMBER_05", "이미 존재하는 아이디입니다.");

    ExceptionCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
