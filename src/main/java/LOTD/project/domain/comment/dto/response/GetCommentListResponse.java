package LOTD.project.domain.comment.dto.response;

import LOTD.project.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GetCommentListResponse {

    List<InnerComment> commentList = new ArrayList<>();


    @Data
    @Builder
    public static class InnerComment {

        private Long commentId;
        private Long parentCommentId;
        private String content;

    }


    /**
    public GetCommentListResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.parentCommentId = comment.getParentCommentId();
        this.content = comment.getContent();
    }*/

}
