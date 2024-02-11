package LOTD.project.domain.Member.dto.response;

import LOTD.project.domain.Member.Member;
import LOTD.project.global.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberSignUpResponse extends BaseResponse {

    private String memberId;
    private String nickName;
    private String email;
    private int age;

    public static MemberSignUpResponse of(Member member) {

        MemberSignUpResponse memberSignUpResponse = new MemberSignUpResponse();
        memberSignUpResponse.setMemberId(member.getMemberId());
        memberSignUpResponse.setNickName(member.getNickName());
        memberSignUpResponse.setEmail(member.getEmail());
        memberSignUpResponse.setAge(member.getAge());

        return memberSignUpResponse;
    }
}
