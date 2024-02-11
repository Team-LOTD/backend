package LOTD.project.domain.Member.oauth2.dto.info;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoInfo {

    private Long id;
    private String nickName;
    private String email;

    public KakaoInfo(Long id, String nickName, String email) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
    }

}
