package LOTD.project.domain.member.controller;

import LOTD.project.domain.member.dto.request.MemberLoginRequest;
import LOTD.project.domain.member.dto.request.MemberSignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "회원가입/로그인 API")
public interface MemberControllerDoc {


    @Operation(summary = "회원 아이디 중복 체크" , description = "회원 아이디를 중복 체크합니다.")
    ResponseEntity<?> checkMemberId(@RequestParam String memberId);


    @Operation(summary = "회원 닉네임 중복 체크" , description = "회원 닉네임을 중복 체크합니다.")
    ResponseEntity<?> checkNickname(@RequestParam String nickname);

    @Operation(summary = "회원가입" , description = "회원가입을 진행합니다.")
    ResponseEntity<?> signUp(@RequestBody @Valid MemberSignUpRequest memberSignUpRequest, BindingResult bindingResult);

    @Operation(summary = "로그인" , description = "로그인을 진행합니다")
    ResponseEntity<?> login(@RequestBody @Valid MemberLoginRequest memberLoginRequest, BindingResult bindingResult);

    @Operation(summary = "로그아웃" , description = "로그아웃을 진행합니다")
    ResponseEntity<?> logout(HttpServletRequest request);




}
