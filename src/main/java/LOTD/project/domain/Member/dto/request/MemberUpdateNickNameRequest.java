package LOTD.project.domain.Member.dto.request;


import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class MemberUpdateNickNameRequest {

    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳, 숫자로만 입력해주세요.")
    private String nickName;
}
