package LOTD.project.domain.member.oauth2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverToken {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private int expiresIn;

}
