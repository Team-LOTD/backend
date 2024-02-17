package LOTD.project.domain.Member.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberUpdateNickNameRequest {

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "닉네임은 특수문자를 제외한 2~20자리로 입력해주세요")
    private String nickName;
}
