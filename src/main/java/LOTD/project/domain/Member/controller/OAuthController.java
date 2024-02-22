package LOTD.project.domain.Member.controller;

import LOTD.project.domain.Member.dto.response.LoginResponse;
import LOTD.project.domain.Member.oauth2.dto.request.SocialSignUpRequest;
import LOTD.project.domain.Member.oauth2.dto.response.KakaoInfo;
import LOTD.project.domain.Member.oauth2.dto.response.NaverInfo;
import LOTD.project.domain.Member.oauth2.service.KakaoService;
import LOTD.project.domain.Member.oauth2.service.NaverService;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.response.BaseResponse;
import LOTD.project.global.response.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final BaseResponse baseResponse;
    private final KakaoService kakaoService;
    private final NaverService naverService;
    /**
     * 카카오 로그인 (프론트단으로부터 인가코드 전달 받음)
     */
    @GetMapping("/oauth/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        LoginResponse kakaoLoginResponse = kakaoService.kakaoLogin(code,response);
        if (kakaoLoginResponse.getId() == null) {
            KakaoInfo kakaoInfo = kakaoService.getMemberInfoToSend(kakaoLoginResponse.getAccessToken());
            return ResponseEntity.ok().body(kakaoInfo);
        }
        else {
            return baseResponse.success(HttpStatus.OK,kakaoLoginResponse,"로그인에 성공했습니다.");
        }

    }

    @GetMapping("/naver/login")
    public ResponseEntity<?> naverLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        LoginResponse naverLoginResponse = naverService.naverLogin(code,response);
        if (naverLoginResponse.getId() == null) {
            NaverInfo naverInfo = naverService.getMemberInfoToSend(naverLoginResponse.getAccessToken());
            return ResponseEntity.ok().body(naverInfo);
        }
        else {
            return baseResponse.success(HttpStatus.OK,naverLoginResponse,"로그인에 성공했습니다.");
        }
    }

    /**
     * 카카오 회원가입(로그인) 첫 로그인 시만 진행
     * @param socialSignUpRequest
     * @param response
     * @return
     */
    @PostMapping("/kakao/signup_and_login")
    public ResponseEntity<?> kakaoSignUpAndLogin(@RequestBody @Valid SocialSignUpRequest socialSignUpRequest, HttpServletResponse response) {
        return ResponseEntity.ok().body(kakaoService.kakaoSignUpAndLogin(socialSignUpRequest,response));
    }

    @PostMapping("/naver/signup_and_login")
    public ResponseEntity<?> naverSignUpAndLogin(@RequestBody @Valid SocialSignUpRequest socialSignUpRequest, HttpServletResponse response) {
        return ResponseEntity.ok().body(naverService.naverSignUpAndLogin(socialSignUpRequest,response));
    }


    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandle(BaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getExceptionCode());
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

}
