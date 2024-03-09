package LOTD.project.domain.post.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreatePostRequest {

    private Long categoryId;

    @NotBlank
    private String memberId;

    @NotBlank
    private String title;

    private String content;
    private String image;

    private Long commentsCount;

    private Long likeCount;

    private Long hits;

}
