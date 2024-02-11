package LOTD.project.global.response;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class BaseResponse {

    @Getter
    @Builder
    private static class Body {
        private Status status;
        private Object data;
        private String message;
    }

    /**
     * 성공 응답
     * @param status
     * @param data
     */
    public ResponseEntity<?> success(HttpStatus status, Object data, String message) {
        Body body = Body.builder()
                .status(Status.SUCCESS)
                .data(data)
                .message(message)
                .build();
        return ResponseEntity.status(status).body(body);
    }

    /**
     * 생성, 수정, 삭제 등이 성공했을 때 주로 사용(data에 담을게 없을 때)
     * @param status HTTP status 코드와 응답 결과 메세지만 전달
     * @return
     */

    public ResponseEntity<?> success(HttpStatus status, String message) {
        Body body = Body.builder()
                .status(Status.SUCCESS)
                .message(message)
                .build();
        return ResponseEntity.status(status).body(body);
    }

    /**
     * 실패 응답
     * @param status
     * @param message
     * @return
     */

    public ResponseEntity<?> fail(HttpStatus status, String message) {
        Body body = Body.builder()
                .status(Status.FAIL)
                .message(message)
                .build();
        return ResponseEntity.status(status).body(body);
    }

    /**
     * 서버 에러
     */
    public ResponseEntity<?> error(Object data) {
        Body body = Body.builder()
                .status(Status.ERROR)
                .data(data)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
