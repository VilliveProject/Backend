package com.villive.Backend.dto;

import com.villive.Backend.domain.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String memberId;
    private Long userId;
    private Long postsId;


    /* Entity -> Dto*/
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.memberId = comment.getMember().getMemberId();
        this.userId = comment.getMember().getId();
        this.postsId = comment.getPosts().getId();
    }
}
