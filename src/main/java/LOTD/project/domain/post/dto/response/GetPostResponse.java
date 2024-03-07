package LOTD.project.domain.post.dto.response;

import LOTD.project.domain.comment.Comment;
import LOTD.project.domain.comment.dto.response.GetCommentReplyListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 게시글 조회 응답
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {

    private Long postId;
    private Long categoryId;
    private String memberId;
    private String title;
    private String content;
    private String image;
    private Long commentsCount;
    private Long likeCount;
    private Long hits;
    private String creator;
    private LocalDateTime CreatedDate;
    private List<GetCommentReplyListResponse> commentReplyList;

}
