package com.villive.Backend.dto;

import com.villive.Backend.domain.Comment;
import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.Posts;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
@ToString
public class CommentRequestDto {

    private String content;

    private Boolean isAnonymous;

    @Builder
    public CommentRequestDto(String content, Boolean isAnonymous){
        this.content = content;
        this.isAnonymous = isAnonymous;
    }

    public Comment toEntity(){
        return Comment.builder()
                .content(content)
                .isAnonymous(isAnonymous)
                .build();
    }


}
