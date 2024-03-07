package LOTD.project.domain.category;

import LOTD.project.domain.post.Post;
import LOTD.project.global.audit.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @NotBlank
    @Column(name = "CATEGORY_NM")
    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Post> post;

    public void updateCategory(String categoryName) {
        this.categoryName = categoryName;
    }

}
