package LOTD.project.domain.Member.oauth2.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleToken {

    private String tokenType;
    private String accessToken;
    private String idToken;
    private int expiresIn;
    private String refreshToken;
    private int refreshTokenExpiresIn;
    private String scope;

}
