package LOTD.project.domain.category.service;

import LOTD.project.domain.category.Category;
import LOTD.project.domain.category.dto.request.CreateCategoryRequest;
import LOTD.project.domain.category.dto.request.UpdateCategoryRequest;
import LOTD.project.domain.category.dto.response.CreateCategoryResponse;
import LOTD.project.domain.category.dto.response.GetCategoryListResponse;
import LOTD.project.domain.category.repository.CategoryRepository;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public GetCategoryListResponse getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();

        List<GetCategoryListResponse.InnerCategoryList> response = new ArrayList<>();

        response = categoryList.stream()
                .map(data -> GetCategoryListResponse.InnerCategoryList.builder()
                        .categoryId(data.getCategoryId())
                        .categoryName(data.getCategoryName())
                        .build())
                .collect(Collectors.toList());

        return GetCategoryListResponse.builder().categoryList(response).build();

    }

    @Transactional
    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        if (categoryRepository.findByCategoryName(request.getCategoryName()).isPresent()) {
            throw new BaseException(ExceptionCode.DATA_ALREADY_EXISTS);
        }

        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .build();

        categoryRepository.save(category);

        return CreateCategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();

    }

    @Transactional
    public void updateCategory(UpdateCategoryRequest request, Long categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId).orElseThrow(()-> new BaseException(ExceptionCode.DATA_NOT_FOUND));

        category.updateCategory(request.getCategoryName());

        categoryRepository.save(category);

    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId).orElseThrow(()-> new BaseException(ExceptionCode.DATA_NOT_FOUND));
        categoryRepository.delete(category);
    }

}
