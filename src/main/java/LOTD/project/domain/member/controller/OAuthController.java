package LOTD.project.domain.member.controller;

import LOTD.project.domain.member.dto.response.LoginResponse;
import LOTD.project.domain.member.oauth2.dto.request.GoogleSignUpRequest;
import LOTD.project.domain.member.oauth2.dto.request.KakaoSignUpRequest;
import LOTD.project.domain.member.oauth2.dto.request.NaverSignUpRequest;
import LOTD.project.domain.member.oauth2.dto.response.GoogleInfo;
import LOTD.project.domain.member.oauth2.dto.response.KakaoInfo;
import LOTD.project.domain.member.oauth2.dto.response.NaverInfo;
import LOTD.project.domain.member.oauth2.service.GoogleService;
import LOTD.project.domain.member.oauth2.service.KakaoService;
import LOTD.project.domain.member.oauth2.service.NaverService;
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
public class OAuthController implements OAuthControllerDoc {

    private final BaseResponse baseResponse;
    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final GoogleService googleService;
    /**
     * 카카오 로그인 (프론트단으로부터 인가코드 전달 받음)
     */
    @GetMapping("/oauth/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        LoginResponse kakaoLoginResponse = kakaoService.kakaoLogin(code,response);
        if (kakaoLoginResponse.getMemberId() == null) {
            KakaoInfo kakaoInfo = kakaoService.getMemberInfoToSend(kakaoLoginResponse.getAccessToken());
            return ResponseEntity.ok().body(kakaoInfo);
        }
        else {
            return baseResponse.success(HttpStatus.OK,kakaoLoginResponse,"로그인에 성공했습니다.");
        }

    }

    @GetMapping("/oauth/naver/login")
    public ResponseEntity<?> naverLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        LoginResponse naverLoginResponse = naverService.naverLogin(code,response);
        if (naverLoginResponse.getMemberId() == null) {
            NaverInfo naverInfo = naverService.getMemberInfoToSend(naverLoginResponse.getAccessToken());
            return ResponseEntity.ok().body(naverInfo);
        }
        else {
            return baseResponse.success(HttpStatus.OK,naverLoginResponse,"로그인에 성공했습니다.");
        }
    }

    @GetMapping("/oauth/google/login")
    public ResponseEntity<?> googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        LoginResponse googleLoginResponse = googleService.googleLogin(code,response);
        if (googleLoginResponse.getMemberId() == null) {
            GoogleInfo googleInfo = googleService.getMemberInfoToSend(googleLoginResponse.getAccessToken());
            return ResponseEntity.ok().body(googleInfo);
        }
        else {
            return baseResponse.success(HttpStatus.OK,googleLoginResponse,"로그인에 성공했습니다.");
        }
    }

    /**
     * 카카오 회원가입(로그인) 첫 로그인 시만 진행
     * @param
     * @param response
     * @return
     */
    @PostMapping("/oauth/kakao/signup")
    public ResponseEntity<?> kakaoSignUpAndLogin(@RequestBody @Valid KakaoSignUpRequest kakaoSigUpRequest, HttpServletResponse response) {
        return ResponseEntity.ok().body(kakaoService.kakaoSignUpAndLogin(kakaoSigUpRequest,response));
    }

    @PostMapping("/oauth/naver/signup")
    public ResponseEntity<?> naverSignUpAndLogin(@RequestBody @Valid NaverSignUpRequest naverSignUpRequest, HttpServletResponse response) {
        return ResponseEntity.ok().body(naverService.naverSignUpAndLogin(naverSignUpRequest,response));
    }

    @PostMapping("/oauth/google/signup")
    public ResponseEntity<?> googleSignUpAndLogin(@RequestBody @Valid GoogleSignUpRequest googleSignUpRequest, HttpServletResponse response) {
        return ResponseEntity.ok().body(googleService.googleSignUpAndLogin(googleSignUpRequest,response));
    }




    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandle(BaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getExceptionCode());
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

}
