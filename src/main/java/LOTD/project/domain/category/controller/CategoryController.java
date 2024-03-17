package LOTD.project.domain.category.controller;

import LOTD.project.domain.category.dto.request.CreateCategoryRequest;
import LOTD.project.domain.category.dto.request.UpdateCategoryRequest;
import LOTD.project.domain.category.dto.response.CreateCategoryResponse;
import LOTD.project.domain.category.dto.response.GetCategoryListResponse;
import LOTD.project.domain.category.service.CategoryService;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.response.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerDoc{

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<GetCategoryListResponse> getCategoryList() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @PostMapping("/categories")
    public ResponseEntity<CreateCategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(request));
    }

    @PutMapping("/categories")
    public void updateCategory(@RequestBody @Valid UpdateCategoryRequest request, @RequestParam(name = "category_id") Long categoryId) {
        categoryService.updateCategory(request,categoryId);
    }

    @DeleteMapping("/categories")
    public void deleteCategory(@RequestParam(name = "category_id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @ResponseBody
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandle(BaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getExceptionCode());
        return ResponseEntity.status(exceptionResponse.getStatus()).body(exceptionResponse);
    }


}
