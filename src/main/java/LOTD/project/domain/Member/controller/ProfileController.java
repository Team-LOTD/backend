package LOTD.project.domain.Member.controller;

import LOTD.project.domain.Member.dto.request.*;
import LOTD.project.domain.Member.dto.response.MyPageResponse;
import LOTD.project.domain.Member.service.ProfileService;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.response.BaseResponse;
import LOTD.project.global.response.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {


    private final ProfileService profileService;
    private final BaseResponse baseResponse;

    @PutMapping("/members/{id}/updateNickname")
    public ResponseEntity<?> updateMemberNickName(@RequestBody MemberUpdateNickNameRequest memberUpdateNickNameRequest, @PathVariable("id") Long id) {
        profileService.updateMemberNickName(memberUpdateNickNameRequest, id);
        return baseResponse.success(HttpStatus.OK,"닉네임이 정상적으로 수정되었습니다.");
    }

    @PutMapping("/members/{id}/updateAge")
    public ResponseEntity<?> updateMemberAge(@RequestBody MemberUpdateAgeRequest memberUpdateAgeRequest, @PathVariable("id") Long id) {
        profileService.updateMemberAge(memberUpdateAgeRequest, id);
        return baseResponse.success(HttpStatus.OK,"나이가 정상적으로 수정되었습니다.");
    }

    @PutMapping("/members/{id}/updateEmail")
    public ResponseEntity<?> updateMemberEmail(@RequestBody MemberUpdateEmailRequest memberUpdateEmailRequest, @PathVariable("id") Long id) {
        profileService.updateMemberEmail(memberUpdateEmailRequest, id);
        return baseResponse.success(HttpStatus.OK,"이메일이 정상적으로 수정되었습니다.");
    }


    @PutMapping("/members/{id}/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable("id") Long id) {
        String asIsPassword = changePasswordRequest.getAsIsPassword();
        String toBePassword = changePasswordRequest.getToBePassword();
        String confirmToBePassword = changePasswordRequest.getConfirmToBePassword();

        if (!toBePassword.equals(confirmToBePassword)) {
            return baseResponse.fail(HttpStatus.BAD_REQUEST,"비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        profileService.changePassword(changePasswordRequest.getAsIsPassword(), changePasswordRequest.getToBePassword(), id);
        return baseResponse.success(HttpStatus.OK,"비밀번호가 변경되었습니다.");
    }

    @DeleteMapping("/members/{id}/delete")
    public ResponseEntity<?> delMember(@RequestBody DeleteMemberRequest deleteMemberRequest, @PathVariable("id") Long id) {
        profileService.delMember(deleteMemberRequest.getPassword(),id);
        return baseResponse.success(HttpStatus.OK,"탈퇴 완료되었습니다.");
    }

    @GetMapping("members/{id}")
    public ResponseEntity<?> MyPage(@PathVariable("id") Long id) {
        MyPageResponse myPageResponse = profileService.myPage(id);
        return baseResponse.success(HttpStatus.OK,myPageResponse,"내 정보 조회 완료");
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandle(BaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getExceptionCode());
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }

}
