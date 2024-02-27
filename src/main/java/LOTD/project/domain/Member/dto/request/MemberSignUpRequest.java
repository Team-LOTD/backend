package LOTD.project.domain.Member.dto.request;


import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class MemberSignUpRequest {


    private boolean memberIdChecked;
    private boolean nickNameChecked;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9@]{4,25}$", message = "아이디는 알파벳 대소문자 혹은 숫자 혹은 @로만 이루어지면서, 4~25자 내외로 입력해주세요.")
    private String memberId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d~!@#$%^&*()+|=._]{8,20}$",
            message = "비밀번호는 8~20 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "확인 비밀번호는 8~20 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String confirmPassword;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "닉네임은 특수문자를 제외한 2~20자리로 입력해주세요")
    private String nickName;

    @Size(max = 35, message = "이메일은 35자리 이하로 입력해주세요")
    @Pattern(regexp = "(^\\w+@\\w+\\.\\w+(\\.\\w+)?$)|", message = "올바르지 않은 이메일 형식입니다.")
    private String email;


}
