package LOTD.project.domain.Member.oauth2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverInfo {
    private String naverMemberId;
    private String email;
}
