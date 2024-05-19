package com.villive.Backend.dto;

import com.villive.Backend.domain.Complain;
import com.villive.Backend.domain.ComplainStatus;
import com.villive.Backend.domain.ComplainType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class ComplainRequestDto {

    private ComplainType type;
    private String contents;
    private String title;
    private ComplainStatus status;

    /* DTO -> Entity */
    public Complain toEntity() {
        return Complain.builder()
                .type(type)
                .status(status.접수)
                .title(title)
                .contents(contents)
                .build();
    }

}
