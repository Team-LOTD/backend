package LOTD.project.domain.comment;


import LOTD.project.domain.member.Member;
import LOTD.project.domain.post.Post;
import LOTD.project.global.audit.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @Column(name = "PARENT_COMMENT_ID")
    private Long parentCommentId;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void updateComment(String content) {
        this.content = content;
    }

}
