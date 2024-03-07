package LOTD.project.domain.comment.dto.response;

import LOTD.project.domain.comment.Comment;
import LOTD.project.domain.comment.Reply;

import java.util.List;

public class GetCommentReplyListResponse {

    private Long commentId;

    private String content;

    private List<Reply> reply;

    public GetCommentReplyListResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.reply = comment.getReply();
    }

}
