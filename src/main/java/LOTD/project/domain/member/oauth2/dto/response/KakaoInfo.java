package LOTD.project.domain.member.oauth2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoInfo {
    private String kakaoMemberId;
    private String email;

}
