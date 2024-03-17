package LOTD.project.domain.heart.controller;

import LOTD.project.domain.heart.dto.request.HeartClickRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "좋아요 API")
public interface HeartControllerDoc {

    @Operation(summary = "좋아요" , description = "좋아요 클릭 시 좋아요 수를 1 증가시킵니다.")
    void heartClick(HeartClickRequest request);

    //@Operation(summary = "좋아요 취소" , description = "게시글을 생성합니다.")
    //void heartCancel

}
