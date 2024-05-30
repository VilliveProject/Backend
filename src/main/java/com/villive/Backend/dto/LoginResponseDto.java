package com.villive.Backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private boolean hasBuildingCode;

    public LoginResponseDto(String token, boolean hasBuildingCode) {
        this.token = token;
        this.hasBuildingCode = hasBuildingCode;
    }

}

