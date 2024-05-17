package com.villive.Backend.dto;

import lombok.Getter;

@Getter
public class UpdatePwdRequestDto {

    private String currentPassword;
    private String newPassword;

}
