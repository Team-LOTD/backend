package LOTD.project.domain.comment;

import LOTD.project.domain.member.Member;
import LOTD.project.global.audit.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_ID")
    private Long replyId;

    @Column(name = "CONTENT")
    private String content;


    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;


}
