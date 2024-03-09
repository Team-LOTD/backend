package LOTD.project.domain.category.controller;

import LOTD.project.domain.category.dto.request.CreateCategoryRequest;
import LOTD.project.domain.category.dto.request.UpdateCategoryRequest;
import LOTD.project.domain.category.dto.response.CreateCategoryResponse;
import LOTD.project.domain.category.dto.response.GetCategoryListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Tag(name = "카테고리 API")
public interface CategoryControllerDoc {


    @Operation(summary = "카테고리 목록 조회", description = "카테고리 목록을 조회합니다.")
    ResponseEntity<GetCategoryListResponse> getCategoryList();

    @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다.")
    ResponseEntity<CreateCategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request);

    @Operation(summary = "카테고리 수정", description = "카테고리를 수정합니다.")
    void updateCategory(@RequestBody @Valid UpdateCategoryRequest request, @RequestParam(name = "category_id") Long categoryId);

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다.")
    void deleteCategory(@RequestParam(name = "category_id") Long categoryId);

}
