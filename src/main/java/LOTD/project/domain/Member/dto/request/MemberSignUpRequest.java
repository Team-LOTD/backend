package LOTD.project.domain.Member.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberSignUpRequest {


    private boolean memberIdChecked;
    private boolean nickNameChecked;

    @NotBlank(message = "아이디를 입력해주세요") @Size(min = 4, max = 25, message = "아이디는 4~25자 내외로 입력해주세요")
    private String memberId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 8~20 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "확인 비밀번호는 8~20 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String confirmPassword;

    @NotBlank(message = "닉네임을 입력해주세요") @Size(min=2, message = "닉네임이 너무 짧습니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳, 숫자로만 입력해주세요.")
    private String nickName;

    @NotBlank(message = "이메일을 입력해주세요") @Size(min = 6, max = 35, message = "이메일은 6~35자 내외로 입력해주세요")
    private String email;

    private int age;

}
