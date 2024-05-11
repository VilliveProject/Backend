package com.villive.Backend.dto;

import com.villive.Backend.domain.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private Boolean isAnonymous;
    private String writer;
    private String createdDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.isAnonymous = comment.getIsAnonymous();
        this.writer = comment.getWriter();
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}

