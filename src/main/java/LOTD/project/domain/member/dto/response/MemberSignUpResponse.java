package LOTD.project.domain.member.dto.response;

import LOTD.project.domain.member.Member;
import LOTD.project.global.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberSignUpResponse extends BaseResponse {

    private String memberId;
    private String nickname;
    private String email;

    public static MemberSignUpResponse of(Member member) {

        MemberSignUpResponse memberSignUpResponse = new MemberSignUpResponse();
        memberSignUpResponse.setMemberId(member.getMemberId());
        memberSignUpResponse.setNickname(member.getNickname());
        memberSignUpResponse.setEmail(member.getEmail());

        return memberSignUpResponse;
    }
}
