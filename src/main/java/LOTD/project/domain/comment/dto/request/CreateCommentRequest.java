package LOTD.project.domain.comment.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateCommentRequest {

    private String parentCommentId;
    private String content;

    @NotBlank
    private Long postId;

    @NotBlank
    private String memberId;
}
