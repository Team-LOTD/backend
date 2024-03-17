package LOTD.project.domain.comment.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateCommentRequest {

    private Long commentId;
    private String content;
}
