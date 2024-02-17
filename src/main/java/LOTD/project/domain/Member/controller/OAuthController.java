package LOTD.project.domain.Member.controller;

import LOTD.project.domain.Member.dto.response.LoginResponse;
import LOTD.project.domain.Member.oauth2.service.KakaoService;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.response.BaseResponse;
import LOTD.project.global.response.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final BaseResponse baseResponse;
    private final KakaoService kakaoService;

    /**
     * 카카오 로그인 (프론트단으로부터 인가코드 전달 받음)
     */
    @GetMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        LoginResponse kakaoLoginResponse = kakaoService.kakaoLogin(code,response);
        return baseResponse.success(HttpStatus.OK,kakaoLoginResponse,"로그인에 성공했습니다.");
    }











    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandle(BaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getExceptionCode());
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

}
