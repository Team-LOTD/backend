package LOTD.project.global.audit;


import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "CREATE_DTM")
    private LocalDateTime createDateTime;   // 생성일시

    @LastModifiedDate
    @Column(nullable = false, name = "UPDATE_DTM")
    private LocalDateTime updateDateTime;   // 수정일시

}