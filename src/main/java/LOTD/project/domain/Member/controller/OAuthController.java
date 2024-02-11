package LOTD.project.domain.Member.controller;

import LOTD.project.domain.Member.dto.response.LoginResponse;
import LOTD.project.domain.Member.oauth2.service.KakaoService;
import LOTD.project.global.response.BaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final BaseResponse baseResponse;
    private final KakaoService kakaoService;
    /**
     * 카카오 로그인
     */
    @PostMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        LoginResponse kakaoLoginResponse = kakaoService.kakaoLogin(code,response);
        return baseResponse.success(HttpStatus.OK,kakaoLoginResponse,"로그인에 성공했습니다.");
    }



}
