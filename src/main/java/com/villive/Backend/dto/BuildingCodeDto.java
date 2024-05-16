package com.villive.Backend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BuildingCodeDto {

    @JsonProperty("building_code")
    private Integer buildingCode;

    @Builder
    public BuildingCodeDto(Integer buildingCode){
        this.buildingCode = buildingCode;
    }

}
