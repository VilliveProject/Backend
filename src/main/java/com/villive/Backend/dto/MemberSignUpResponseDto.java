package com.villive.Backend.dto;

import com.villive.Backend.domain.Member;
import com.villive.Backend.domain.MemberRole;
import lombok.Getter;

@Getter
public class MemberSignUpResponseDto {

    private Long id;
    private String memberId;
    private String address;
    private MemberRole role;

    public MemberSignUpResponseDto(Member member){
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.address = member.getAddress();
        this.role = member.getRole();

    }
}
