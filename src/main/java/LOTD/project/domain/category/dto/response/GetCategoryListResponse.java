package LOTD.project.domain.category.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class GetCategoryListResponse {

    List<InnerCategoryList> categoryList = new ArrayList<>();

    @Getter
    @Builder
    public static class InnerCategoryList {
        private Long categoryId;
        private String categoryName;
    }

}
