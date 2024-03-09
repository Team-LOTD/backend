package LOTD.project.global.audit;


import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class)

@Getter
@MappedSuperclass
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @JoinColumn(updatable = false)
    //@ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "Creator")
    private String creator;                // 생성자

    @LastModifiedBy
    //@ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "updater")
    private String updater;                // 수정자

}
