package LOTD.project.domain.comment.dto.response;

import LOTD.project.domain.comment.Comment;

import java.util.List;

public class GetCommentListResponse {

    private Long commentId;

    private Long parentCommentId;

    private String content;
    public GetCommentListResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.parentCommentId = comment.getParentCommentId();
        this.content = comment.getContent();
    }

}
