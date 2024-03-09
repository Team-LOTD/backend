package LOTD.project.domain.category.repository;

import LOTD.project.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByCategoryId(Long categoryId);
    Optional<Category> findByCategoryName(String categoryName);
    List<Category> findAll();

}
