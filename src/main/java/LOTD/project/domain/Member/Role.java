package LOTD.project.domain.Member;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ROLE_MEMBER("ROLE_MEMBER"), ROLE_ADMIN("ROLE_ADMIN");

    private final String key;

}
