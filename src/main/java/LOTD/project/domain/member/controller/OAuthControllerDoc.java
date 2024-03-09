package LOTD.project.domain.member.controller;


import LOTD.project.domain.member.oauth2.dto.request.GoogleSignUpRequest;
import LOTD.project.domain.member.oauth2.dto.request.KakaoSignUpRequest;
import LOTD.project.domain.member.oauth2.dto.request.NaverSignUpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "소셜 로그인 API")
public interface OAuthControllerDoc {

    @Operation(summary = "카카오 로그인" , description = "카카오 로그인을 진행합니다.")
    ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException;

    @Operation(summary = "네이버 로그인" , description = "네이버 로그인을 진행합니다.")
    ResponseEntity<?> naverLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException;

    @Operation(summary = "구글 로그인" , description = "구글 로그인을 진행합니다.")
    ResponseEntity<?> googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException;

    @Operation(summary = "카카오 회원가입/로그인" , description = "처음 로그인하는 유저는 닉네임을 추가 입력하고 회원가입/로그인을 진행합니다.")
    ResponseEntity<?> kakaoSignUpAndLogin(@RequestBody @Valid KakaoSignUpRequest kakaoSigUpRequest, HttpServletResponse response);

    @Operation(summary = "네이버 회원가입/로그인" , description = "처음 로그인하는 유저는 닉네임을 추가 입력하고 회원가입/로그인을 진행합니다.")
    ResponseEntity<?> naverSignUpAndLogin(@RequestBody @Valid NaverSignUpRequest naverSignUpRequest, HttpServletResponse response);

    @Operation(summary = "구글 회원가입/로그인" , description = "처음 로그인하는 유저는 닉네임을 추가 입력하고 회원가입/로그인을 진행합니다.")
    ResponseEntity<?> googleSignUpAndLogin(@RequestBody @Valid GoogleSignUpRequest googleSignUpRequest, HttpServletResponse response);


}
