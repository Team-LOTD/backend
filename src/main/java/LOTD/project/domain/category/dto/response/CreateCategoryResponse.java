package LOTD.project.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CreateCategoryResponse {

    private Long categoryId;
    private String categoryName;

}
