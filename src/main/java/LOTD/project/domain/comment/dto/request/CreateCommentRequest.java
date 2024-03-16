package LOTD.project.domain.comment.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateCommentRequest {

    private Long parentCommentId;
    private String content;

    private Long postId;

    @NotBlank
    private String memberId;
}
