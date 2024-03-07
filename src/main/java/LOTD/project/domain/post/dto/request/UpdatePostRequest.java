package LOTD.project.domain.post.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdatePostRequest {


    @NotBlank
    private String title;

    private String content;
    private String image;


}
