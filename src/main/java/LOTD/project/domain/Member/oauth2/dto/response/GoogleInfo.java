package LOTD.project.domain.Member.oauth2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleInfo {
    private String googleMemberId;
    private String email;

}