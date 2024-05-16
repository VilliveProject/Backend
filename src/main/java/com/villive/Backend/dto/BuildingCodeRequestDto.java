package com.villive.Backend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.villive.Backend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BuildingCodeRequestDto {

    @JsonProperty("building_code")
    private String buildingCode;
    private String address;

    /* DTO -> Entity */
    public Member toEntity() {
        return Member.builder()
                .buildingCode(buildingCode)
                .address(address)
                .build();
    }

}
