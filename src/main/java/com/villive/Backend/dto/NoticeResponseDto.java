package com.villive.Backend.dto;

import com.villive.Backend.domain.Notice;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class NoticeResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String writer;
    private String createdDate;

    public NoticeResponseDto(Notice notice){
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.contents = notice.getContents();
        this.writer = notice.getWriter();
        this.createdDate = notice.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
