package com.villive.Backend.dto;

import com.villive.Backend.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class NoticeRequestDto {

    private String title;
    private String contents;

    public Notice toEntity() {
        return Notice.builder()
                .title(title)
                .contents(contents)
                .build();
    }
}
