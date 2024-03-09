package LOTD.project.domain.post.repository;


import LOTD.project.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();

    List<Post> findAllByCreatorContains(String creator); // 작성자로 검색


    List<Post> findAllByTitleContains(String text); // 제목 + 내용으로 검색
    List<Post> findAllByContentContains(String text); // 제목 + 내용으로 검색

    Optional<Post> findByPostId(Long postId);

}
