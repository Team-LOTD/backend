package LOTD.project.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePostResponse {

    private Long postId;
    private String title;

}
