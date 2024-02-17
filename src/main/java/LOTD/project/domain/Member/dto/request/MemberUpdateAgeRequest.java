package LOTD.project.domain.Member.dto.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
public class MemberUpdateAgeRequest {

    @Min(value = 1)
    @Max(value = 150)
    private int age;
}
