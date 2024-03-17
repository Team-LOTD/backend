package LOTD.project.domain.heart;

import LOTD.project.domain.member.Member;
import LOTD.project.domain.post.Post;
import LOTD.project.global.audit.BaseEntity;

import javax.persistence.*;

@Entity
public class Heart extends BaseEntity {

    @Id
    @Column(name = "HEART_ID")
    private Long heartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(name = "HEART_YN")
    private String heartYn;


}
