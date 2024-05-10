package com.villive.Backend.dto;

import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private Long id;
    private String memberId;
    private String password;
    private String name;
    private String address;
    private MemberRole role;

    public SignUpResponseDto(Member member){
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.address = member.getAddress();
        this.role = member.getRole();

    }
}
