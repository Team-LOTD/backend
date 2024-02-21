package LOTD.project.domain.Member.oauth2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoInfo {
    private String kakaoMemberId;
    private String email;

}
