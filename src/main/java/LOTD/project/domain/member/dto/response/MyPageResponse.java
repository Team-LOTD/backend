package LOTD.project.domain.member.dto.response;

import LOTD.project.domain.member.oauth2.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyPageResponse {

    private String memberId;
    private String nickName;
    private String email;
    private SocialType socialType;

}
