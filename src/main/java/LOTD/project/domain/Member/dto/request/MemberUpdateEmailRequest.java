package LOTD.project.domain.Member.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberUpdateEmailRequest {

    @Size(max = 35, message = "이메일은 35자리 이하로 입력해주세요")
    @Pattern(regexp = "(^\\w+@\\w+\\.\\w+(\\.\\w+)?$)|", message = "올바르지 않은 이메일 형식입니다.")
    private String email;
}
