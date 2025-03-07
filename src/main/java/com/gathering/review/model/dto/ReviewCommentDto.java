package com.gathering.review.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gathering.common.base.jpa.BaseTimeEntity;
import com.gathering.review.model.constant.StatusType;
import com.gathering.review.model.entitiy.BookReview;
import com.gathering.review.model.entitiy.ReviewComment;
import com.gathering.user.model.entitiy.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class ReviewCommentDto{

    private long id;
    private long userId;
    private long reviewId;
    private String content;
    private long parent;
    private int orders;
    private StatusType status;
    private String userName;

    // 댓글 목록
    private String profile;
    private String createTime;


    // 리뷰 상세 댓글 목록 조회 생성자
    public ReviewCommentDto(long id, long userId, String content, int orders, String profile
    ,String userName, String createTime) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.orders = orders;
        this.profile = profile;
        this.userName = userName;
        this.createTime = createTime;
    }

    public static ReviewCommentDto formEntity(ReviewComment comment) {
        return ReviewCommentDto.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .reviewId(comment.getReview().getId())
                .content(comment.getContent())
                .parent(comment.getParent())
                .orders(comment.getOrders())
                .status(comment.getStatus())
                .build();
    }
}
