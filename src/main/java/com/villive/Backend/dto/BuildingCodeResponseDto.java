package com.villive.Backend.dto;

import com.villive.Backend.domain.Member;
import lombok.Getter;

@Getter
public class BuildingCodeResponseDto {

    private Long id;
    private String buildingCode;
    private String address;

    public BuildingCodeResponseDto(Member member){
        this.id = member.getId();
        this.buildingCode = member.getBuildingCode();
        this.address = member.getAddress();
    }

}
