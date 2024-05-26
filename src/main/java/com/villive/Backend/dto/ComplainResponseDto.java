package com.villive.Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.villive.Backend.domain.Complain;
import com.villive.Backend.domain.ComplainStatus;
import com.villive.Backend.domain.ComplainType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ComplainResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String writer;
    private String createdDate;
    private ComplainStatus status;
    private ComplainType type;

    public ComplainResponseDto(Complain complain) {
        this.id = complain.getId();
        this.title = complain.getTitle();
        this.contents = complain.getContents();
        this.writer = complain.getWriter();
        this.createdDate = complain.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.status = complain.getStatus();
        this.type = complain.getType();
    }

}


