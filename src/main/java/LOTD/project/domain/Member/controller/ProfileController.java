package LOTD.project.domain.Member.controller;

import LOTD.project.domain.Member.dto.request.*;
import LOTD.project.domain.Member.dto.response.MyPageResponse;
import LOTD.project.domain.Member.service.ProfileService;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.exception.ExceptionCode;
import LOTD.project.global.response.BaseResponse;
import LOTD.project.global.response.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProfileController {


    private final ProfileService profileService;
    private final BaseResponse baseResponse;

    @PutMapping("/members/nickname/update")
    public ResponseEntity<?> updateMemberNickName(@RequestBody @Valid MemberUpdateNickNameRequest memberUpdateNickNameRequest ,BindingResult bindingResult,
                                                  @RequestParam(name = "id") Long id) throws Exception {

        // 유효성 검사를 통과하지 못한 경우 바로 에러 메시지 반환
        if (bindingResult.hasErrors()) {
            return baseResponse.fail(HttpStatus.BAD_REQUEST, "닉네임은 특수문자를 제외한 2~20자리로 입력해주세요");
        }

        profileService.updateMemberNickName(memberUpdateNickNameRequest, id);
        return baseResponse.success(HttpStatus.OK,"닉네임이 정상적으로 수정되었습니다.");
    }


    @PutMapping("/members/email/update")
    public ResponseEntity<?> updateMemberEmail(@RequestBody @Valid MemberUpdateEmailRequest memberUpdateEmailRequest, BindingResult bindingResult,
                                               @RequestParam(name = "id") Long id) throws Exception {

        // 유효성 검사를 통과하지 못한 경우 바로 에러 메시지 반환
        if (bindingResult.hasErrors()) {
            return baseResponse.fail(HttpStatus.BAD_REQUEST, "올바르지 않은 이메일 형식입니다. 이메일을 올바르게 입력해주세요. (최대 35자) ");
        }

        profileService.updateMemberEmail(memberUpdateEmailRequest, id);
        return baseResponse.success(HttpStatus.OK,"이메일이 정상적으로 수정되었습니다.");
    }


    @PutMapping("/members/password/change")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest, BindingResult bindingResult,
                                            @RequestParam(name = "id") Long id) throws Exception{

        String asIsPassword = changePasswordRequest.getAsIsPassword();
        String toBePassword = changePasswordRequest.getToBePassword();
        String confirmToBePassword = changePasswordRequest.getConfirmToBePassword();

        // 유효성 검사를 통과하지 못한 경우 바로 에러 메시지 반환
        if (bindingResult.hasErrors()) {
            return baseResponse.fail(HttpStatus.BAD_REQUEST, "비밀번호는 8~20 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.");
        }

        if (!toBePassword.equals(confirmToBePassword)) {
            throw new BaseException(ExceptionCode.NOT_SAME_PASSWORD);
        }

        profileService.changePassword(changePasswordRequest.getAsIsPassword(), changePasswordRequest.getToBePassword(), id);
        return baseResponse.success(HttpStatus.OK,"비밀번호가 변경되었습니다.");
    }

    @DeleteMapping("/members/delete")
    public ResponseEntity<?> delMember(@RequestBody @Valid DeleteMemberRequest deleteMemberRequest, BindingResult bindingResult, @RequestParam(name = "id") Long id) {

        // 유효성 검사를 통과하지 못한 경우 바로 에러 메시지 반환
        if (bindingResult.hasErrors()) {
            return baseResponse.fail(HttpStatus.BAD_REQUEST, "비밀번호는 8~20 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.");
        }

        profileService.delMember(deleteMemberRequest.getPassword(),id);
        return baseResponse.success(HttpStatus.OK,"탈퇴 완료되었습니다.");
    }

    @GetMapping("/members")
    public ResponseEntity<?> MyPage(@RequestParam(name = "id") Long id) {
        MyPageResponse myPageResponse = profileService.myPage(id);
        return baseResponse.success(HttpStatus.OK,myPageResponse,"내 정보 조회 완료");
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandle(BaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getExceptionCode());
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

}
