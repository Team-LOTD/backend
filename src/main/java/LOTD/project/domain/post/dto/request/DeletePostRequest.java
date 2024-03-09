package LOTD.project.domain.post.dto.request;

import lombok.Getter;

@Getter
public class DeletePostRequest {

    private Long postId;
    private String memberId;
}
