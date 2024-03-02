package LOTD.project.domain.member.oauth2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverInfo {
    private String naverMemberId;
    private String email;
}
